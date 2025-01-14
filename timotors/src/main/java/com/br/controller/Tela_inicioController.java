/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 *
 * @author felip
 */
public class Tela_inicioController implements Initializable {
    
    @FXML
    private Button btnVeiculo;
  
    @FXML
    private Button btnFuncionario;

    @FXML
    private Button btnCliente;

    @FXML
    private StackPane pilhaPainel;
    
    @FXML
    private void telaveiculo(ActionEvent event) {
        carregarPagina("Tela_veiculo");
    }

    @FXML
    private void telafuncionario(ActionEvent event) {
        carregarPagina("Tela_funcionario");
    }

    @FXML
    private void telacliente(ActionEvent event) {
        carregarPagina("Tela_cliente");
    }
    
    private void carregarPagina(String name){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/telas/" + name + ".fxml"));
            System.out.println("Entrou no carregar...");
        } catch (IOException ex) {
            Logger.getLogger(Tela_inicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pilhaPainel.getChildren().clear();
        pilhaPainel.getChildren().add(root);

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
