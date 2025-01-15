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

import com.br.dao.FuncionarioDAO;
import com.br.model.Funcionario;

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
public class Tela_funcionarioController implements Initializable {

    @FXML
    private Button btnaddfuncionario;

    @FXML
    private TableView<Funcionario> tabelaFuncionario;

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
    private ImageView imagemEditar;

    @FXML
    private ImageView imagemRemover;

    @FXML
    public void carregarDadosTabela() {
        tabelaFuncionario.getItems().clear();
        colunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        FuncionarioDAO funcDao = new FuncionarioDAO();
        ArrayList<Funcionario> funcionarios = funcDao.buscarTodos();
        System.out.println("˜˜carregando dados----" + funcionarios.size());

        //comando para passar para javaFx
        ObservableList<Funcionario> itensFuncionarioFX = FXCollections.observableArrayList(funcionarios);
        // Jogar na tabela.
        tabelaFuncionario.setItems(itensFuncionarioFX);
    }

    @FXML
    private void addfuncionario(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Cadastro_funcionarioController.class.getResource("/com/br/Cadastro_funcionario.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Cadastro de Funcionário");
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
            FuncionarioDAO dao = new FuncionarioDAO();
            Funcionario funcionario = tabelaFuncionario.getSelectionModel().getSelectedItem();

            if (funcionario == null) {
                Alert erroAlert = new Alert(Alert.AlertType.ERROR);
                erroAlert.setContentText("Selecione um funcionário para editar");
                erroAlert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/br/Cadastro_funcionario.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(Tela_funcionarioController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Cadastro_funcionarioController controller = loader.getController();

                controller.getTxtNome().setText(funcionario.getNome()); // set no formulário
                controller.getTxtCpf().setText(funcionario.getCpf());
                controller.getTxtTelefone().setText(funcionario.getTelefone());
                controller.getTxtEmail().setText(funcionario.getEmail());
                controller.setUpdate(Boolean.TRUE);
                controller.setIdFuncionario(funcionario.getId());

                Parent parent = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            }
        });

        imagemRemover.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            FuncionarioDAO dao = new FuncionarioDAO();
            Funcionario funcionario = tabelaFuncionario.getSelectionModel().getSelectedItem();

            if (funcionario == null) {
                Alert erroAlert = new Alert(Alert.AlertType.ERROR);
                erroAlert.setContentText("Selecione um funcionário para remover");
                erroAlert.showAndWait();
            } else {
                dao.delete(funcionario.getId());
                carregarDadosTabela();
            }
        });

    }
}
