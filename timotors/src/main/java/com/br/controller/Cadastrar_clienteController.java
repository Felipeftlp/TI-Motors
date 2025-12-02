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
import com.br.model.EstadoVeiculo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author felip
 */
public class Cadastrar_clienteController implements Initializable {

    //@ spec_public nullable
    @FXML
    private Button AddCli;

    //@ spec_public nullable
    @FXML
    private TextField txtNome;

    //@ spec_public nullable
    @FXML
    private TextField txtCpf;

    //@ spec_public nullable
    @FXML
    private TextField txtEmail;

    //@ spec_public nullable
    @FXML
    private TextField txtTelefone;

    //@ spec_public nullable
    @FXML
    private ComboBox<EstadoVeiculo> comboBoxInteresse;

    //@ spec_public nullable
    @FXML
    private TextField txtEndereco;

    //@ spec_public
    private boolean update;

    //@ spec_public
    private int idCliente;

    /*@
      @ requires nome != null && cpf != null && telefone != null && email != null && endereco != null;
      @ ensures \result == (!nome.equals("") && !cpf.equals("") && !telefone.equals("") && !email.equals("") && !endereco.equals("") && interesse != null);
      @ pure
      @*/
    public static boolean validarCamposObrigatorios(String nome, String cpf, String telefone, 
                                            String email, String endereco, EstadoVeiculo interesse) {
        return !nome.equals("") && !cpf.equals("") && !telefone.equals("") 
               && !email.equals("") && !endereco.equals("") && interesse != null;
    }
    
    //assumimos que a construção do objeto é segura porque o Modelo já foi verificado
    /*@
      @ requires nome != null && cpf != null && telefone != null && email != null && endereco != null && interesse != null;
      @ ensures \result != null;
      @*/
    //@ skipesc
    private Cliente criarCliente(String nome, String cpf, String telefone, 
                                 String email, EstadoVeiculo interesse, String endereco) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        cliente.setInteresse(interesse);
        cliente.setEndereco(endereco);
        return cliente;
    }

    //@ skipesc
    private void exibirAlertaCamposPendentes() {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle("Campos pendentes");
        alerta.setHeaderText("Campos pendentes");
        alerta.setContentText("Preencha todos os campos antes de prosseguir!");
        alerta.showAndWait();
    }

    //@ skipesc
    private void exibirAlertaSucesso() {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Cadastro de cliente");
        alerta.setHeaderText("Cadastro de cliente");
        alerta.setContentText("Cliente cadastrado com sucesso!!");
        alerta.showAndWait();
    }

    /*@ 
      @ requires txtNome != null && txtCpf != null && txtTelefone != null && txtEmail != null && comboBoxInteresse != null && txtEndereco != null;
      @*/
    //@ skipesc
    @FXML
    @SuppressWarnings("unused")
    private void cadastrarCliente(ActionEvent event) {
        String nome = txtNome.getText();
        String CPF = txtCpf.getText();
        String Telefone = txtTelefone.getText();
        String Email = txtEmail.getText();
        EstadoVeiculo Interesse = comboBoxInteresse.getValue();
        String Endereco = txtEndereco.getText();

        if (!validarCamposObrigatorios(nome, CPF, Telefone, Email, Endereco, Interesse)) {
            exibirAlertaCamposPendentes();
            return;
        }

        Cliente cliente = criarCliente(nome, CPF, Telefone, Email, Interesse, Endereco);
        ClienteDAO dao = new ClienteDAO();

        if (update) {
            cliente.setId(idCliente);
            dao.update(cliente);
            fecharModal();
        } else {
            dao.create(cliente);
            exibirAlertaSucesso();
            limparDadosFormulario();
        }
    }

    //@ skipesc
    public void fecharModal() {
        Stage stage = (Stage) AddCli.getScene().getWindow();

        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    //@ skipesc
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Adicionando itens ao ComboBox
        comboBoxInteresse.getItems().addAll(EstadoVeiculo.NOVO, EstadoVeiculo.SEMINOVO, EstadoVeiculo.USADO);
    }

    //@ skipesc
    private void limparDadosFormulario() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        comboBoxInteresse.setValue(EstadoVeiculo.NOVO);
    }

    /*@ pure nullable @*/
    //@ skipesc
    public Button getAddCli() {
        return AddCli;
    }

    //@ skipesc
    public void setAddCli(Button AddCli) {
        this.AddCli = AddCli;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public TextField getTxtNome() {
        return txtNome;
    }

    //@ skipesc
    public void setTxtNome(TextField txtNome) {
        this.txtNome = txtNome;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public TextField getTxtCpf() {
        return txtCpf;
    }

    //@ skipesc
    public void setTxtCpf(TextField txtCpf) {
        this.txtCpf = txtCpf;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public TextField getTxtEmail() {
        return txtEmail;
    }

    //@ skipesc
    public void setTxtEmail(TextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public TextField getTxtTelefone() {
        return txtTelefone;
    }

    //@ skipesc
    public void setTxtTelefone(TextField txtTelefone) {
        this.txtTelefone = txtTelefone;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public ComboBox<EstadoVeiculo> getComboBoxInteresse() {
        return comboBoxInteresse;
    }

    //@ skipesc
    public void setComboBoxInteresse(ComboBox<EstadoVeiculo> comboBoxInteresse) {
        this.comboBoxInteresse = comboBoxInteresse;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public TextField getTxtEndereco() {
        return txtEndereco;
    }

    //@ skipesc
    public void setTxtEndereco(TextField txtEndereco) {
        this.txtEndereco = txtEndereco;
    }

    /*@ pure @*/
    //@ skipesc
    public boolean isUpdate() {
        return update;
    }

    //@ assignable this.update;
    //@ skipesc
    public void setUpdate(boolean update) {
        this.update = update;
    }

    /*@ pure @*/
    //@ skipesc
    public int getIdCliente() {
        return idCliente;
    }

    //@ assignable this.idCliente;
    //@ skipesc
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
