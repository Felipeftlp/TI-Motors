/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.br.dao.ClienteDAO;
import com.br.model.Cliente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author felip
 */
public class Tela_clienteController implements Initializable {

    @FXML
    private Button btnaddcliente;

    @FXML
    private TableView<Cliente> tabelaCliente;

    @FXML
    private TableColumn<?, ?> colunaID;

    @FXML
    private TableColumn<?, ?> colunaNome;

    @FXML
    private TableColumn<?, ?> colunaCPF;

    @FXML
    private TableColumn<?, ?> colunaTelefone;

    @FXML
    private TableColumn<?, ?> colunaEmail;
    
    @FXML
    private TableColumn<?, ?> colunaInteresse;

    @FXML
    private ImageView imagemEditar;

    @FXML
    private ImageView imagemRemover;

    @FXML
    public void carregarDadosTabela() {
        tabelaCliente.getItems().clear();
        colunaID.setCellValueFactory(new PropertyValueFactory<>("id_cliente"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaInteresse.setCellValueFactory(new PropertyValueFactory<>("interesse"));

        ClienteDAO clienteDao = new ClienteDAO();
        ArrayList<Cliente> clientes = clienteDao.buscarTodos();
        System.out.println("˜˜carregando dados----" + clientes.size());

        //comando para passar para javaFx
        ObservableList<Cliente> itensClienteFX = FXCollections.observableArrayList(clientes);
        // Jogar na tabela.
        tabelaCliente.setItems(itensClienteFX);
    }

    @FXML
    private void addcliente(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Cadastrar_clienteController.class.getResource("/telas/Cadastrar_cliente.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Cadastro de Clientes");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());
        stage.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarDadosTabela();

        imagemEditar.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            ClienteDAO dao = new ClienteDAO();
            Cliente cliente = tabelaCliente.getSelectionModel().getSelectedItem();

            if (cliente == null) {
                Alert erroAlert = new Alert(Alert.AlertType.ERROR);
                erroAlert.setContentText("Selecione um cliente para editar");
                erroAlert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/telas/Cadastrar_cliente.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(Tela_clienteController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Cadastrar_clienteController controller = loader.getController();

                controller.getTxtNome().setText(cliente.getNome()); // set no formulário
                controller.getTxtCpf().setText(cliente.getCpf());
                controller.getTxtTelefone().setText(cliente.getTelefone());
                controller.getTxtEmail().setText(cliente.getEmail());
                controller.getTxtInteresse().setText(cliente.getInteresse());
                controller.setUpdate(Boolean.TRUE);
                controller.setIdCliente(cliente.getId_cliente());

                Parent parent = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            }
        });

        imagemRemover.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            ClienteDAO dao = new ClienteDAO();
            Cliente cliente = tabelaCliente.getSelectionModel().getSelectedItem();

            if (cliente == null) {
                Alert erroAlert = new Alert(Alert.AlertType.ERROR);
                erroAlert.setContentText("Selecione um cliente para remover");
                erroAlert.showAndWait();
            } else {
                dao.delete(cliente.getId_cliente());
                carregarDadosTabela();
            }
        });

    }
}
