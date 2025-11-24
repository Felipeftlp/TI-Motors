package com.br.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.br.dao.ClienteDAO;
import com.br.dao.FuncionarioDAO;
import com.br.dao.VeiculoDAO;
import com.br.model.Cliente;
import com.br.model.Funcionario;
import com.br.model.StatusVeiculo;
import com.br.model.Veiculo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox; // Novo
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 * Refatorado para Design by Contract (JML) e Simulação Financeira
 */
public class Vender_veiculoController implements Initializable {

    // --- Componentes da Tela (UI) ---
    @FXML private Button btnVender;
    @FXML private ComboBox<Cliente> comboBoxCliente;
    @FXML private ComboBox<Funcionario> comboBoxFuncionario;
    @FXML private TextField txtInfoVeiculo;

    // --- Novos Componentes Financeiros (Adicionar no FXML) ---
    @FXML private CheckBox chkFinanciamento;
    @FXML private TextField txtEntrada;
    @FXML private ComboBox<Integer> comboBoxParcelas;

    private Veiculo veiculo;
    private static final double TAXA_JUROS_MENSAL = 0.02; // 2% ao mês

    /**
     * Calcula a comissão (10%).
     * //@ requires valorVeiculo >= 0;
     * //@ ensures \result == valorVeiculo * 0.10;
     * //@ pure
     */
    public double calcularComissao(double valorVeiculo) {
        return valorVeiculo * 0.10;
    }

    /**
     * Calcula a parcela usando Tabela Price (Juros Compostos).
     * //@ requires valorVeiculo > 0 && entrada >= 0 && meses > 0;
     * //@ pure
     */
    public double calcularParcelaPrice(double valorVeiculo, double entrada, int meses) {
        double saldoDevedor = valorVeiculo - entrada;
        
        if (saldoDevedor <= 0) return 0.0; // Se entrada pagar tudo

        // Fórmula: PMT = PV * [ i / (1 - (1+i)^-n) ]
        double fator = Math.pow(1 + TAXA_JUROS_MENSAL, -meses);
        double valorParcela = saldoDevedor * (TAXA_JUROS_MENSAL / (1 - fator));
        
        return valorParcela;
    }

    /**
     * Valida os dados antes de processar. Lança exceção com a mensagem de erro.
     * //@ requires cliente != null && funcionario != null && veiculo != null;
     */
    public void validarDadosVenda(Cliente cliente, Funcionario funcionario, Veiculo veiculo, 
                                  boolean isFinanciado, double entrada, Integer parcelas) throws IllegalArgumentException {
        
        if (cliente == null || funcionario == null) {
            throw new IllegalArgumentException("Selecione o cliente e o funcionário antes de prosseguir!");
        }
        if (veiculo == null) {
            throw new IllegalArgumentException("Nenhum veículo selecionado para venda.");
        }

        if (isFinanciado) {
            double precoVeiculo = Double.parseDouble(veiculo.getPreco());

            if (entrada < 0) {
                throw new IllegalArgumentException("O valor da entrada não pode ser negativo.");
            }
            if (entrada >= precoVeiculo) {
                throw new IllegalArgumentException("A entrada é maior ou igual ao valor do veículo. Não use financiamento.");
            }
            if (parcelas == null || parcelas <= 0) {
                throw new IllegalArgumentException("Selecione a quantidade de parcelas.");
            }
        }
    }

    /**
     * Persistência: Isola o acesso ao DAO.
     */
    public void persistirVenda(Veiculo veiculo, Funcionario funcionario, double comissao) throws Exception {
        // 1. Atualizar Veículo
        veiculo.setStatus(StatusVeiculo.VENDIDO);
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        boolean veiculoAtualizado = veiculoDAO.update(veiculo);

        if (!veiculoAtualizado) {
            veiculo.setStatus(StatusVeiculo.DISPONIVEL); // Rollback manual
            throw new Exception("Não foi possível atualizar o status do veículo no banco de dados!");
        }

        // 2. Atualizar Comissão
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        boolean comissaoAtualizada = funcionarioDAO.atualizarComissao(funcionario.getId(), comissao);

        if (!comissaoAtualizada) {
            throw new Exception("Venda registrada, mas falha ao atualizar comissão do funcionário!");
        }
    }

    @FXML
    @SuppressWarnings("unused")
    private void efetuarVenda(ActionEvent event) {
        Stage ownerStage = (Stage) btnVender.getScene().getWindow();

        try {
            // 1. Coleta de Dados
            Cliente cliente = comboBoxCliente.getValue();
            Funcionario funcionario = comboBoxFuncionario.getValue();
            
            // Dados Financeiros
            boolean financiado = chkFinanciamento != null && chkFinanciamento.isSelected();
            double entrada = 0.0;
            Integer parcelas = 0;
            
            // Tratamento seguro para campos financeiros (caso null no parse)
            if (financiado) {
                String txt = txtEntrada.getText();
                entrada = (txt == null || txt.trim().isEmpty()) ? 0.0 : Double.parseDouble(txt);
                parcelas = comboBoxParcelas.getValue();
            }

            // 2. Validação
            validarDadosVenda(cliente, funcionario, this.veiculo, financiado, entrada, parcelas);

            // 3. Cálculos
            double precoVeiculo = Double.parseDouble(this.veiculo.getPreco());
            double comissao = calcularComissao(precoVeiculo);
            
            double valorParcela = 0.0;
            double totalPago = precoVeiculo;

            if (financiado) {
                valorParcela = calcularParcelaPrice(precoVeiculo, entrada, parcelas);
                totalPago = entrada + (valorParcela * parcelas);
            }

            // 4. Persistência
            persistirVenda(this.veiculo, funcionario, comissao);

            // 5. Feedback Sucesso
            mostrarAlertaSucesso(ownerStage, cliente, funcionario, comissao, financiado, valorParcela, parcelas, totalPago);
            fecharModal();

        } catch (NumberFormatException e) {
            mostrarAlertaErro(ownerStage, "Erro de Formato", "Verifique se os valores numéricos (Preço, Entrada) estão corretos.");
        } catch (IllegalArgumentException e) {
            mostrarAlertaErro(ownerStage, "Dados Inválidos", e.getMessage());
        } catch (Exception e) {
            mostrarAlertaErro(ownerStage, "Erro no Sistema", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Habilita/Desabilita campos de financiamento baseado no CheckBox
     */
    @FXML
    private void alternarFinanciamento() {
        if (chkFinanciamento != null) {
            boolean ativo = chkFinanciamento.isSelected();
            if (txtEntrada != null) {
                txtEntrada.setDisable(!ativo);
                if (!ativo) txtEntrada.setText("");
            }
            if (comboBoxParcelas != null) {
                comboBoxParcelas.setDisable(!ativo);
                if (!ativo) comboBoxParcelas.setValue(null);
            }
        }
    }

    // --- Alertas Auxiliares ---

    private void mostrarAlertaErro(Stage owner, String header, String content) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.initOwner(owner);
        alerta.setTitle("Erro");
        alerta.setHeaderText(header);
        alerta.setContentText(content);
        alerta.showAndWait();
    }

    private void mostrarAlertaSucesso(Stage owner, Cliente cliente, Funcionario funcionario, double comissao, 
                                      boolean financiado, double valorParcela, int parcelas, double totalPago) { // Correção aqui: int parcelas
        StringBuilder sb = new StringBuilder();
        sb.append("Veículo vendido para: ").append(cliente.getNome());
        sb.append("\nVendedor: ").append(funcionario.getNome());
        
        if (financiado) {
            sb.append("\n\n--- FINANCIAMENTO APROVADO ---");
            sb.append("\nCondição: ").append(parcelas).append("x de R$ ").append(String.format("%.2f", valorParcela));
            sb.append("\nTotal a pagar: R$ ").append(String.format("%.2f", totalPago));
            sb.append("\n(Juros de ").append(String.format("%.1f", TAXA_JUROS_MENSAL * 100)).append("% a.m incluso)");
        } else {
            sb.append("\n\nPagamento À VISTA: R$ ").append(String.format("%.2f", totalPago));
        }
        
        sb.append("\n\nComissão Gerada: R$ ").append(String.format("%.2f", comissao));

        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.initOwner(owner);
        alerta.setTitle("Sucesso");
        alerta.setHeaderText("Venda Efetuada!");
        alerta.setContentText(sb.toString());
        alerta.setResizable(true);
        alerta.getDialogPane().setPrefWidth(450);
        alerta.showAndWait();
    }

    public void fecharModal() {
        Stage stage = (Stage) btnVender.getScene().getWindow();
        stage.close();
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        if (veiculo != null) {
            txtInfoVeiculo.setText(veiculo.getMarca() + " " + veiculo.getModelo() + " | " + veiculo.getPreco());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarDadosCombos();
        configurarFinanciamentoUI();
    }
    
    private void configurarFinanciamentoUI() {
        // Inicializa itens do financiamento se existirem no FXML
        if (comboBoxParcelas != null) {
            comboBoxParcelas.getItems().addAll(12, 24, 36, 48, 60);
            comboBoxParcelas.setDisable(true);
        }
        if (txtEntrada != null) {
            txtEntrada.setDisable(true);
        }
        
        // Listener para o CheckBox (alternativa ao onAction no FXML)
        if (chkFinanciamento != null) {
            chkFinanciamento.selectedProperty().addListener((obs, oldVal, newVal) -> alternarFinanciamento());
        }
    }

    private void carregarDadosCombos() {
        // --- CLIENTES ---
        ClienteDAO clienteDAO = new ClienteDAO();
        ArrayList<Cliente> clientes = clienteDAO.buscarTodos();
        ObservableList<Cliente> clientesFX = FXCollections.observableArrayList(clientes);
        comboBoxCliente.setItems(clientesFX);
        
        // Configuração de Renderização (CORREÇÃO DO BUG DE EXIBIÇÃO)
        // Usamos um Callback para garantir que tanto a lista quanto o botão mostrem o nome
        Callback<javafx.scene.control.ListView<Cliente>, ListCell<Cliente>> fabricaCelulaCliente = param -> new ListCell<Cliente>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " - CPF: " + item.getCpf());
                }
            }
        };
        comboBoxCliente.setCellFactory(fabricaCelulaCliente);
        comboBoxCliente.setButtonCell(fabricaCelulaCliente.call(null)); // Aplica a mesma lógica ao botão selecionado

        // --- FUNCIONÁRIOS ---
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ArrayList<Funcionario> funcionarios = funcionarioDAO.buscarTodos();
        ObservableList<Funcionario> funcionariosFX = FXCollections.observableArrayList(funcionarios);
        comboBoxFuncionario.setItems(funcionariosFX);

        // Configuração de Renderização
        Callback<javafx.scene.control.ListView<Funcionario>, ListCell<Funcionario>> fabricaCelulaFuncionario = param -> new ListCell<Funcionario>() {
            @Override
            protected void updateItem(Funcionario item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " - Cargo: " + (item.getCargo() != null ? item.getCargo().toString() : "N/A"));
                }
            }
        };
        comboBoxFuncionario.setCellFactory(fabricaCelulaFuncionario);
        comboBoxFuncionario.setButtonCell(fabricaCelulaFuncionario.call(null));
    }
}