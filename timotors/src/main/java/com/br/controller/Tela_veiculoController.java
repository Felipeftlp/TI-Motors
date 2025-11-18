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

import com.br.dao.VeiculoDAO;
import com.br.model.EstadoVeiculo;
import com.br.model.StatusVeiculo;
import com.br.model.Veiculo;

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
public class Tela_veiculoController implements Initializable {
    

    @FXML
    private TableView<Veiculo> TabelaVeiculo;

    @FXML
    private TableColumn<Veiculo, Integer> ColunaID;

    @FXML
    private TableColumn<Veiculo, String> ColunaMarca;

    @FXML
    private TableColumn<Veiculo, String> ColunaAno;

    @FXML
    private TableColumn<Veiculo, String> ColunaModelo;

    @FXML
    private TableColumn<Veiculo, String> ColunaCor;

    @FXML
    private TableColumn<Veiculo, EstadoVeiculo> ColunaEstado;

    @FXML
    private TableColumn<Veiculo, String> ColunaPreco;
    
    @FXML
    private TableColumn<Veiculo, StatusVeiculo> ColunaStatus;
    
    @FXML
    private ImageView imagemEditar;

    @FXML
    private ImageView imagemRemover;
    
    @FXML
    private Button btnVender;

    @FXML
    public void carregarDadosTabela() {
        TabelaVeiculo.getItems().clear();
        ColunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        ColunaAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        ColunaModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        ColunaCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        ColunaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        ColunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        ColunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        VeiculoDAO veicDao = new VeiculoDAO();
        ArrayList<Veiculo> veiculos = veicDao.buscarTodos();
        System.out.println("carregando dados----" + veiculos.size());

        ObservableList<Veiculo> itensveiculosFX = FXCollections.observableArrayList(veiculos);
        TabelaVeiculo.setItems(itensveiculosFX);
    }

    @FXML
    @SuppressWarnings("unused")
    private void addveiculo(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Cadastro_veiculoController.class.getResource("/com/br/Cadastro_veiculo.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Cadastro de Veículo");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarDadosTabela();
        
        imagemEditar.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            Veiculo veiculo = TabelaVeiculo.getSelectionModel().getSelectedItem();

            if (veiculo == null) {
                Alert erroAlert = new Alert(Alert.AlertType.ERROR);
                erroAlert.setContentText("Selecione um Veículo para editar");
                erroAlert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/br/Cadastro_veiculo.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(Tela_veiculoController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Cadastro_veiculoController controller = loader.getController();

                controller.getTxtMarca().setText(veiculo.getMarca());
                controller.getTxtAno().setText(veiculo.getAno());
                controller.getTxtModelo().setText(veiculo.getModelo());
                controller.getTxtCor().setText(veiculo.getCor());
                controller.getComboBoxEstado().setValue(veiculo.getEstado());
                controller.getTxtpreco().setText(veiculo.getPreco());
                controller.setUpdate(Boolean.TRUE);
                controller.setIdVeiculo(veiculo.getId());

                Parent parent = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            }
        });

        imagemRemover.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            VeiculoDAO dao = new VeiculoDAO();
            Veiculo veiculo = TabelaVeiculo.getSelectionModel().getSelectedItem();

            if (veiculo == null) {
                Alert erroAlert = new Alert(Alert.AlertType.ERROR);
                erroAlert.setContentText("Selecione um veículo para remover");
                erroAlert.showAndWait();
            } else {
                Alert erroAlert = new Alert(Alert.AlertType.INFORMATION);
                erroAlert.setContentText("Veículo removido com sucesso");
                erroAlert.showAndWait();
                dao.delete(veiculo.getId());
                carregarDadosTabela();
            }
        });
        
        btnVender.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            Veiculo veiculo = TabelaVeiculo.getSelectionModel().getSelectedItem();

            if (veiculo == null) {
                Alert erroAlert = new Alert(Alert.AlertType.ERROR);
                erroAlert.setContentText("Selecione um veículo para vender");
                erroAlert.showAndWait();
            } else if (veiculo.getStatus() == StatusVeiculo.VENDIDO) {
                Alert erroAlert = new Alert(Alert.AlertType.ERROR);
                erroAlert.setContentText("Este veículo já foi vendido");
                erroAlert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/br/Vender_veiculo.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(Tela_veiculoController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Vender_veiculoController controller = loader.getController();
                controller.setVeiculo(veiculo);

                Parent parent = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UTILITY);
                stage.setTitle("Vender Veículo");
                stage.show();
            }
        });
    }  
}
