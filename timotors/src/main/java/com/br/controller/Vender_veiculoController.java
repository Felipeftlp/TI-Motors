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
import javafx.scene.control.CheckBox;
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
    //@ public invariant TAXA_JUROS_MENSAL == 0.02;

    //@ spec_public nullable
    @FXML 
    private Button btnVender;
    
    //@ spec_public nullable
    @FXML 
    private ComboBox<Cliente> comboBoxCliente;
    
    //@ spec_public nullable
    @FXML 
    private ComboBox<Funcionario> comboBoxFuncionario;
    
    //@ spec_public nullable
    @FXML 
    private TextField txtInfoVeiculo;

    //@ spec_public nullable
    @FXML 
    private CheckBox chkFinanciamento;
    
    //@ spec_public nullable
    @FXML 
    private TextField txtEntrada;
    
    //@ spec_public nullable
    @FXML 
    private ComboBox<Integer> comboBoxParcelas;

    /*@ spec_public nullable @*/ 
    private Veiculo veiculo;
    
    /*@ spec_public @*/
    private static final double TAXA_JUROS_MENSAL = 0.02; // 2% ao mês

    /*@ 
      @ public normal_behavior
      @   requires valorVeiculo >= 0;
      @   ensures \result == valorVeiculo * 0.10;
      @   ensures \result >= 0;
      @   pure
      @*/
    public double calcularComissao(double valorVeiculo) {
        return valorVeiculo * 0.10;
    }

    /*@ 
      @ public normal_behavior
      @   requires valorVeiculo > 0;
      @   requires entrada >= 0;
      @   requires meses > 0;
      @   requires entrada < valorVeiculo;
      @   pure
      @*/
    public double calcularParcelaPrice(double valorVeiculo, double entrada, int meses) {
        double saldoDevedor = valorVeiculo - entrada;
        
        if (saldoDevedor <= 0) return 0.0;

        double fator = Math.pow(1 + TAXA_JUROS_MENSAL, -meses);
        
        double denominador = 1 - fator;

        if (denominador == 0) {
            return 0.0;
        }

        double valorParcela = saldoDevedor * (TAXA_JUROS_MENSAL / denominador);
        
        return valorParcela;
    }

    /*@ 
      @ public behavior
      @   assignable \nothing;
      @
      @   signals (IllegalArgumentException) 
      @       (cliente == null || funcionario == null) ||
      @       (veiculo == null) ||
      @       (veiculo != null && veiculo.getPreco() == null) ||
      @       (isFinanciado && (entrada < 0 || parcelas == null || parcelas <= 0));
      @ 
      @   signals (NumberFormatException) isFinanciado; 
      @*/
    public void validarDadosVenda(Cliente cliente, Funcionario funcionario, Veiculo veiculo, 
                                  boolean isFinanciado, double entrada, Integer parcelas) throws IllegalArgumentException {
        
        if (cliente == null || funcionario == null) {
            throw new IllegalArgumentException("Selecione o cliente e o funcionário antes de prosseguir!");
        }
        if (veiculo == null) {
            throw new IllegalArgumentException("Nenhum veículo selecionado para venda.");
        }
        
        /*@ nullable @*/ String precoTexto = veiculo.getPreco();

        if (precoTexto == null) {
             throw new IllegalArgumentException("Erro: O veículo selecionado não possui preço cadastrado.");
        }

        if (isFinanciado) {
            double precoVeiculo = converterDoubleSeguro(precoTexto);

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

    /*@
      @ public normal_behavior
      @   requires veiculo != null;
      @   requires funcionario != null;
      @   requires veiculo.status != StatusVeiculo.VENDIDO; 
      @
      @   ensures veiculo.status == StatusVeiculo.VENDIDO;
      @
      @   assignable veiculo.status;
      @
      @ also
      @
      @ public exceptional_behavior
      @   signals (Exception) true;
      @*/
      //@ skipesc
    public void persistirVenda(Veiculo veiculo, Funcionario funcionario, double comissao) throws Exception {
        veiculo.setStatus(StatusVeiculo.VENDIDO);
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        boolean veiculoAtualizado = veiculoDAO.update(veiculo);

        if (!veiculoAtualizado) {
            veiculo.setStatus(StatusVeiculo.DISPONIVEL); // Rollback manual
            throw new Exception("Não foi possível atualizar o status do veículo no banco de dados!");
        }

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        boolean comissaoAtualizada = funcionarioDAO.atualizarComissao(funcionario.getId(), comissao);

        if (!comissaoAtualizada) {
            throw new Exception("Venda registrada, mas falha ao atualizar comissão do funcionário!");
        }
    }

    //@ skipesc
    @FXML
    @SuppressWarnings("unused")
    private void efetuarVenda(ActionEvent event) {
        Stage ownerStage = (Stage) btnVender.getScene().getWindow();

        try {
            Cliente cliente = comboBoxCliente.getValue();
            Funcionario funcionario = comboBoxFuncionario.getValue();
            
            boolean financiado = chkFinanciamento != null && chkFinanciamento.isSelected();
            double entrada = 0.0;
            Integer parcelas = 0;
            
            if (financiado) {
                String txt = txtEntrada.getText();
                entrada = (txt == null || txt.trim().isEmpty()) ? 0.0 : Double.parseDouble(txt);
                parcelas = comboBoxParcelas.getValue();
            }

            validarDadosVenda(cliente, funcionario, this.veiculo, financiado, entrada, parcelas);

            double precoVeiculo = Double.parseDouble(this.veiculo.getPreco());
            double comissao = calcularComissao(precoVeiculo);
            
            double valorParcela = 0.0;
            double totalPago = precoVeiculo;

            if (financiado) {
                valorParcela = calcularParcelaPrice(precoVeiculo, entrada, parcelas);
                totalPago = entrada + (valorParcela * parcelas);
            }

            persistirVenda(this.veiculo, funcionario, comissao);

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

    //@ skipesc
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

    //@ skipesc
    private void mostrarAlertaErro(Stage owner, String header, String content) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.initOwner(owner);
        alerta.setTitle("Erro");
        alerta.setHeaderText(header);
        alerta.setContentText(content);
        alerta.showAndWait();
    }

    //@ skipesc
    private void mostrarAlertaSucesso(Stage owner, Cliente cliente, Funcionario funcionario, double comissao, 
                                      boolean financiado, double valorParcela, int parcelas, double totalPago) {
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

    //@ skipesc
    public void fecharModal() {
        Stage stage = (Stage) btnVender.getScene().getWindow();
        stage.close();
    }

    /*@ 
      @ requires veiculo != null;
      @ ensures this.veiculo == veiculo;
      @*/
    //@ skipesc
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        if (veiculo != null) {
            txtInfoVeiculo.setText(veiculo.getMarca() + " " + veiculo.getModelo() + " | " + veiculo.getPreco());
        }
    }

    /**
     * Initializes the controller class.
     */
    //@ skipesc
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarDadosCombos();
        configurarFinanciamentoUI();
    }
    
    //@ skipesc
    private void configurarFinanciamentoUI() {
        if (comboBoxParcelas != null) {
            comboBoxParcelas.getItems().addAll(12, 24, 36, 48, 60);
            comboBoxParcelas.setDisable(true);
        }
        if (txtEntrada != null) {
            txtEntrada.setDisable(true);
        }
        
        if (chkFinanciamento != null) {
            chkFinanciamento.selectedProperty().addListener((obs, oldVal, newVal) -> alternarFinanciamento());
        }
    }

    //@ skipesc
    private void carregarDadosCombos() {
        ClienteDAO clienteDAO = new ClienteDAO();
        ArrayList<Cliente> clientes = clienteDAO.buscarTodos();
        ObservableList<Cliente> clientesFX = FXCollections.observableArrayList(clientes);
        comboBoxCliente.setItems(clientesFX);
        
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
        comboBoxCliente.setButtonCell(fabricaCelulaCliente.call(null));

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ArrayList<Funcionario> funcionarios = funcionarioDAO.buscarTodos();
        ObservableList<Funcionario> funcionariosFX = FXCollections.observableArrayList(funcionarios);
        comboBoxFuncionario.setItems(funcionariosFX);

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

    /*@
      @ private normal_behavior
      @   requires valor != null;
      @   ensures \result >= -Double.MAX_VALUE && \result <= Double.MAX_VALUE;
      @   assignable \nothing;
      @
      @ also
      @
      @ private exceptional_behavior
      @   signals (NumberFormatException) true;
      @   assignable \nothing;
      @*/
    //@ skipesc
    private double converterDoubleSeguro(String valor) throws NumberFormatException {
        return Double.parseDouble(valor);
    }

}