/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.br.dao.FuncionarioDAO;
import com.br.model.Cargo;
import com.br.model.Funcionario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author felip
 */
public class Cadastro_funcionarioController implements Initializable {

    //@ spec_public nullable
    @FXML
    private Button addfunc;
    
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
    private ComboBox<Cargo> comboBoxCargo;

    //@ spec_public nullable
    @FXML
    private DatePicker dataAdmissao;
    
    //@ spec_public
    private boolean update;
    
    //@ spec_public
    private int idFuncionario;

    /*@
      @ requires nome != null && cpf != null && telefone != null && email != null;
      @ ensures \result == (!nome.equals("") && !cpf.equals("") && !telefone.equals("") && !email.equals("") && dataAdmissao != null && cargo != null);
      @ pure
      @*/
    public static boolean validarCamposObrigatorios(String nome, String cpf, String telefone, 
                                             String email, LocalDate dataAdmissao, Cargo cargo) {
        return !nome.equals("") && !cpf.equals("") && !telefone.equals("") 
               && !email.equals("") && dataAdmissao != null && cargo != null;
    }

    /*@
      @ requires nome != null && cpf != null && telefone != null && email != null && cargo != null && dataAdmissao != null;
      @ ensures \result != null;
      @*/
    //@ skipesc
    private Funcionario criarFuncionario(String nome, String cpf, String telefone, 
                                         String email, Cargo cargo, LocalDate dataAdmissao) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setTelefone(telefone);
        funcionario.setEmail(email);
        funcionario.setCargo(cargo);
        funcionario.setDataAdmissao(dataAdmissao);
        funcionario.setAnosNaEmpresa();
        funcionario.setSalario();
        return funcionario;
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
        alerta.setTitle("Cadastro de funcionário");
        alerta.setHeaderText("Cadastro de funcionário");
        alerta.setContentText("funcionário cadastrado com sucesso!!");
        alerta.showAndWait();
    }

    /*@ 
      @ requires txtNome != null && txtCpf != null && txtTelefone != null && txtEmail != null && comboBoxCargo != null && dataAdmissao != null;
      @*/
    //@ skipesc
    @FXML
    @SuppressWarnings("unused")
    private void cadastrarFuncionario(ActionEvent event){
        String nome = txtNome.getText();
        String CPF = txtCpf.getText();
        String Telefone = txtTelefone.getText();
        String Email = txtEmail.getText();
        Cargo Cargo = comboBoxCargo.getValue();
        LocalDate DataAdmissao = dataAdmissao.getValue();
        
        if (!validarCamposObrigatorios(nome, CPF, Telefone, Email, DataAdmissao, Cargo)) {
            exibirAlertaCamposPendentes();
            return;
        }

        Funcionario funcionario = criarFuncionario(nome, CPF, Telefone, Email, Cargo, DataAdmissao);
        FuncionarioDAO dao = new FuncionarioDAO();

        if (update) {
            funcionario.setId(idFuncionario);
            dao.update(funcionario);
            fecharModal();
        } else {
            dao.create(funcionario);
            exibirAlertaSucesso();
            limparDadosFormulario();
        }
    }
    
    //@ skipesc
    public void fecharModal(){
        Stage stage = (Stage) addfunc.getScene().getWindow();
            
        stage.close();    
    }
    
    /**
     * Initializes the controller class.
     */
    //@ skipesc
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxCargo.getItems().addAll(Cargo.ANALISTA, Cargo.DIRETOR, Cargo.GERENTE, Cargo.VENDEDOR);
        dataAdmissao.setValue(LocalDate.now());
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
    public ComboBox<Cargo> getComboBoxCargo() {
        return comboBoxCargo;
    }

    //@ skipesc
    public void setComboBoxCargo(ComboBox<Cargo> comboBoxCargo) {
        this.comboBoxCargo = comboBoxCargo;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public DatePicker getDataAdmissao() {
        return dataAdmissao;
    }

    //@ skipesc
    public void setDataAdmissao(DatePicker dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
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
    public int getIdFuncionario() {
        return idFuncionario;
    }

    //@ assignable this.idFuncionario;
    //@ skipesc
    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public Button getAddfunc() {
        return addfunc;
    }

    //@ skipesc
    public void setAddfunc(Button addfunc) {
        this.addfunc = addfunc;
    }
    
    //@ skipesc
    private void limparDadosFormulario() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        dataAdmissao.setValue(LocalDate.now());
        comboBoxCargo.setValue(Cargo.VENDEDOR);
    }
}
