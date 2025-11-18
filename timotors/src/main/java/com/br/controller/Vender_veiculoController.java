/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author felip
 */
public class Vender_veiculoController implements Initializable {

    @FXML
    private Button btnVender;

    @FXML
    private ComboBox<Cliente> comboBoxCliente;

    @FXML
    private ComboBox<Funcionario> comboBoxFuncionario;

    @FXML
    private TextField txtInfoVeiculo;

    private Veiculo veiculo;

    @FXML
    @SuppressWarnings("unused")
    private void efetuarVenda(ActionEvent event) {
        Cliente cliente = comboBoxCliente.getValue();
        Funcionario funcionario = comboBoxFuncionario.getValue();

        // Pega a janela atual (o modal) para usar como "dona" dos alertas
        Stage ownerStage = (Stage) btnVender.getScene().getWindow();

        if (cliente == null || funcionario == null) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.initOwner(ownerStage); // <-- ADICIONADO AQUI
            alerta.setTitle("Campos pendentes");
            alerta.setHeaderText("Campos pendentes");
            alerta.setContentText("Selecione o cliente e o funcionário antes de prosseguir!");
            alerta.showAndWait();
        } else {
            try {
                // Calcular comissão (10% do valor do veículo)
                double precoVeiculo = Double.parseDouble(veiculo.getPreco());
                double comissao = precoVeiculo * 0.10;

                // Atualizar status do veiculo para VENDIDO
                veiculo.setStatus(StatusVeiculo.VENDIDO);
                VeiculoDAO veiculoDAO = new VeiculoDAO();
                boolean veiculoAtualizado = veiculoDAO.update(veiculo);
                
                if (!veiculoAtualizado) {
                    Alert alerta = new Alert(AlertType.ERROR);
                    alerta.initOwner(ownerStage); // <-- ADICIONADO AQUI
                    alerta.setTitle("Erro");
                    alerta.setHeaderText("Erro ao processar venda");
                    alerta.setContentText("Não foi possível atualizar o status do veículo!");
                    alerta.showAndWait();
                    return;
                }

                // Atualizar comissão do funcionário
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                boolean comissaoAtualizada = funcionarioDAO.atualizarComissao(funcionario.getId(), comissao);
                
                if (!comissaoAtualizada) {
                    Alert alerta = new Alert(AlertType.ERROR);
                    alerta.initOwner(ownerStage); // <-- ADICIONADO AQUI
                    alerta.setTitle("Erro");
                    alerta.setHeaderText("Erro ao processar venda");
                    alerta.setContentText("Não foi possível atualizar a comissão do funcionário!");
                    alerta.showAndWait();
                    return;
                }

                // Mostrar mensagem de sucesso
                // Stage mainStage = (Stage) btnVender.getScene().getWindow().getScene().getWindow(); // <- Forma antiga e redundante
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.initOwner(ownerStage); // <-- CORRIGIDO AQUI (usando a variável 'ownerStage')
                // alerta.initModality(Modality.WINDOW_MODAL); // Opcional, initOwner já faz isso
                alerta.setTitle("Venda realizada");
                alerta.setHeaderText("Venda realizada com sucesso!");
                alerta.setContentText("O veículo foi vendido para " + cliente.getNome() + 
                                     "\nFuncionário: " + funcionario.getNome() +
                                     "\nComissão: R$ " + String.format("%.2f", comissao));
                alerta.setResizable(true);
                alerta.getDialogPane().setPrefWidth(450);
                alerta.showAndWait();

                // Fechar a janela
                fecharModal();
            } catch (NumberFormatException e) {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.initOwner(ownerStage); // <-- ADICIONADO AQUI
                alerta.setTitle("Erro");
                alerta.setHeaderText("Erro ao processar venda");
                alerta.setContentText("Erro ao converter o preço do veículo!");
                alerta.showAndWait();
                e.printStackTrace();
            } catch (Exception e) {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.initOwner(ownerStage); // <-- ADICIONADO AQUI
                alerta.setTitle("Erro");
                alerta.setHeaderText("Erro ao processar venda");
                alerta.setContentText("Ocorreu um erro ao processar a venda: " + e.getMessage());
                alerta.showAndWait();
                e.printStackTrace();
            }
        }
    }

    public void fecharModal() {
        Stage stage = (Stage) btnVender.getScene().getWindow();
        stage.close();
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        if (veiculo != null) {
            txtInfoVeiculo.setText(veiculo.getMarca() + 
                                 " " + veiculo.getModelo() + 
                                 " | Ano: " + veiculo.getAno() + 
                                 " | Preço: R$ " + veiculo.getPreco());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Carregar clientes
        ClienteDAO clienteDAO = new ClienteDAO();
        ArrayList<Cliente> clientes = clienteDAO.buscarTodos();
        ObservableList<Cliente> clientesFX = FXCollections.observableArrayList(clientes);
        comboBoxCliente.setItems(clientesFX);
        
        // Configurar exibição do ComboBox de Cliente
        comboBoxCliente.setCellFactory(param -> new ListCell<Cliente>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " - " + item.getCpf());
                }
            }
        });
        
        comboBoxCliente.setButtonCell(new ListCell<Cliente>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " - " + item.getCpf());
                }
            }
        });

        // Carregar funcionários
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ArrayList<Funcionario> funcionarios = funcionarioDAO.buscarTodos();
        ObservableList<Funcionario> funcionariosFX = FXCollections.observableArrayList(funcionarios);
        comboBoxFuncionario.setItems(funcionariosFX);
        
        // Configurar exibição do ComboBox de Funcionário
        comboBoxFuncionario.setCellFactory(param -> new ListCell<Funcionario>() {
            @Override
            protected void updateItem(Funcionario item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " - " + item.getCpf());
                }
            }
        });
        
        comboBoxFuncionario.setButtonCell(new ListCell<Funcionario>() {
            @Override
            protected void updateItem(Funcionario item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " - " + item.getCpf());
                }
            }
        });
    }
}
