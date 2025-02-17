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

    @FXML
    private Button addfunc;
    
    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefone;

    @FXML
    private ComboBox<Cargo> comboBoxCargo;

    @FXML
    private DatePicker dataAdmissao;
    
    private boolean update;
    
    private int idFuncionario;

    @FXML
    @SuppressWarnings("unused")
    private void cadastrarFuncionario(ActionEvent event){
        String nome = txtNome.getText();
        String CPF = txtCpf.getText();
        String Telefone = txtTelefone.getText();
        String Email = txtEmail.getText();
        Cargo Cargo = comboBoxCargo.getValue();
        LocalDate DataAdmissao = dataAdmissao.getValue();
        
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCpf(CPF);
        funcionario.setTelefone(Telefone);
        funcionario.setEmail(Email);
        funcionario.setCargo(Cargo);
        funcionario.setDataAdmissao(DataAdmissao);
        funcionario.setAnosNaEmpresa();
        funcionario.setSalario();
        
        FuncionarioDAO dao = new FuncionarioDAO();
               
        if (nome.equals("") || CPF.equals("") || Telefone.equals("") || Email.equals("") || DataAdmissao == null || Cargo == null) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Campos pendentes");
            alerta.setHeaderText("Campos pendentes");
            alerta.setContentText("Preencha todos os campos antes de prosseguir!");
                           
            alerta.showAndWait();
        } else {
            if (update) {
                funcionario.setId(idFuncionario);
                dao.update(funcionario);

                fecharModal();
            } else {
                dao.create(funcionario);
    
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Cadastro de funcionário");
                alerta.setHeaderText("Cadastro de funcionário");
                alerta.setContentText("funcionário cadastrado com sucesso!!");
    
                alerta.showAndWait();
    
                limparDadosFormulario();
            }
        }
    }
    
    public void fecharModal(){
        Stage stage = (Stage) addfunc.getScene().getWindow();
            
        stage.close();    
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxCargo.getItems().addAll(Cargo.ANALISTA, Cargo.DIRETOR, Cargo.GERENTE, Cargo.VENDEDOR);
        dataAdmissao.setValue(LocalDate.now());
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

    public ComboBox<Cargo> getComboBoxCargo() {
        return comboBoxCargo;
    }

    public void setComboBoxCargo(ComboBox<Cargo> comboBoxCargo) {
        this.comboBoxCargo = comboBoxCargo;
    }

    public DatePicker getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(DatePicker dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Button getAddfunc() {
        return addfunc;
    }

    public void setAddfunc(Button addfunc) {
        this.addfunc = addfunc;
    }
    
    private void limparDadosFormulario() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        dataAdmissao.setValue(LocalDate.now());
        comboBoxCargo.setValue(Cargo.VENDEDOR);
    }
}
