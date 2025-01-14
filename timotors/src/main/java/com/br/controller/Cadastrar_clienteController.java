/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.br.dao.ClienteDAO;
import com.br.model.Cliente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author felip
 */
public class Cadastrar_clienteController implements Initializable {
    @FXML
    private Button AddCli;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtInteresse;

    private boolean update;

    private int idCliente;

    @FXML
    private void cadastrarCliente(ActionEvent event) {
        String nome = txtNome.getText();
        String CPF = txtCpf.getText();
        String Telefone = txtTelefone.getText();
        String Email = txtEmail.getText();
        String Interesse = txtInteresse.getText();

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(CPF);
        cliente.setTelefone(Telefone);
        cliente.setEmail(Email);
        cliente.setInteresse(Interesse);

        ClienteDAO dao = new ClienteDAO();

        if (update) {
            cliente.setId_cliente(idCliente);
            dao.update(cliente);
        } else {
            dao.create(cliente);
            limparDadosFormulario();
        }

        fecharModal();
    }

    public void fecharModal() {
        Stage stage = (Stage) AddCli.getScene().getWindow();

        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void limparDadosFormulario() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtInteresse.setText("");
    }

    public Button getAddCli() {
        return AddCli;
    }

    public void setAddCli(Button AddCli) {
        this.AddCli = AddCli;
    }

    public TextField getTxtNome() {
        return txtNome;
    }

    public void setTxtNome(TextField txtNome) {
        this.txtNome = txtNome;
    }

    public TextField getTxtCpf() {
        return txtCpf;
    }

    public void setTxtCpf(TextField txtCpf) {
        this.txtCpf = txtCpf;
    }

    public TextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(TextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public TextField getTxtTelefone() {
        return txtTelefone;
    }

    public void setTxtTelefone(TextField txtTelefone) {
        this.txtTelefone = txtTelefone;
    }

    public TextField getTxtInteresse() {
        return txtInteresse;
    }

    public void setTxtInteresse(TextField txtInteresse) {
        this.txtInteresse = txtInteresse;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
