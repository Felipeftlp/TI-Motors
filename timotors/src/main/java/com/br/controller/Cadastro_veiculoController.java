/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.br.dao.VeiculoDAO;
import com.br.model.EstadoVeiculo;
import com.br.model.StatusVeiculo;
import com.br.model.Veiculo;

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
public class Cadastro_veiculoController implements Initializable {

    //@ spec_public nullable
    @FXML
    private Button Addveic;

    //@ spec_public nullable
    @FXML
    private TextField txtMarca;

    //@ spec_public nullable
    @FXML
    private TextField txtAno;

    //@ spec_public nullable
    @FXML
    private TextField txtModelo;

    //@ spec_public nullable
    @FXML
    private TextField txtCor;

    //@ spec_public nullable
    @FXML
    private ComboBox<EstadoVeiculo> comboBoxEstado;

    //@ spec_public nullable
    @FXML
    private TextField txtpreco;
    
    //@ spec_public
    private boolean update;
    
    //@ spec_public
    private int idVeiculo;
    
    /*@
      @ requires marca != null && modelo != null && ano != null && cor != null && preco != null;
      @ ensures \result == (!marca.equals("") && !modelo.equals("") && !ano.equals("") && !cor.equals("") && !preco.equals("") && estado != null);
      @ pure
      @*/
    public static boolean validarCamposObrigatorios(String marca, String modelo, String ano, 
                                             String cor, String preco, EstadoVeiculo estado) {
        return !marca.equals("") && !modelo.equals("") && !ano.equals("") 
               && !cor.equals("") && !preco.equals("") && estado != null;
    }
    
    /*@
      @ requires marca != null && modelo != null && ano != null && cor != null && estado != null && preco != null;
      @ ensures \result != null;
      @ ensures !this.update ==> \result.status == StatusVeiculo.DISPONIVEL;
      @*/
    //@ skipesc
    private Veiculo criarVeiculo(String marca, String modelo, String ano, 
                                 String cor, EstadoVeiculo estado, String preco) {
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setAno(ano);
        veiculo.setCor(cor);
        veiculo.setEstado(estado);
        veiculo.setPreco(preco);
        // Se for novo veículo, status padrão é DISPONIVEL
        if (!update) {
            veiculo.setStatus(StatusVeiculo.DISPONIVEL);
        }
        return veiculo;
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
        alerta.setTitle("Cadastro de veículo");
        alerta.setHeaderText("Cadastro de veículo");
        alerta.setContentText("veículo cadastrado com sucesso!!");
        alerta.showAndWait();
    }
    
    /*@ 
      @ requires txtMarca != null && txtModelo != null && txtAno != null && txtCor != null && comboBoxEstado != null && txtpreco != null;
      @*/
    //@ skipesc
    @FXML
    @SuppressWarnings("unused")
    private void cadastrarVeiculo(ActionEvent event){
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();
        String ano = txtAno.getText();
        String cor = txtCor.getText();
        EstadoVeiculo estado = comboBoxEstado.getValue();
        String preco = txtpreco.getText();
        
        if (!validarCamposObrigatorios(marca, modelo, ano, cor, preco, estado)) {
            exibirAlertaCamposPendentes();
            return;
        }

        Veiculo veiculo = criarVeiculo(marca, modelo, ano, cor, estado, preco);
        VeiculoDAO dao = new VeiculoDAO();

        if (update) {
            veiculo.setId(idVeiculo);
            dao.update(veiculo);
            fecharModal();
        } else {
            dao.create(veiculo);
            exibirAlertaSucesso();
            limparDadosFormulario();
        }
    }
    
    //@ skipesc
    public void fecharModal(){
        Stage stage = (Stage) Addveic.getScene().getWindow();
            
        stage.close();    
    }
    
    /**
     * Initializes the controller class.
     */
    //@ skipesc
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Adicionando itens ao ComboBox
        comboBoxEstado.getItems().addAll(EstadoVeiculo.NOVO, EstadoVeiculo.SEMINOVO, EstadoVeiculo.USADO);
    }    

    /*@ pure nullable @*/
    //@ skipesc
    public Button getAddveic() {
        return Addveic;
    }

    //@ skipesc
    public void setAddveic(Button Addveic) {
        this.Addveic = Addveic;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public TextField getTxtMarca() {
        return txtMarca;
    }

    //@ skipesc
    public void setTxtMarca(TextField txtMarca) {
        this.txtMarca = txtMarca;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public TextField getTxtAno() {
        return txtAno;
    }

    //@ skipesc
    public void setTxtAno(TextField txtAno) {
        this.txtAno = txtAno;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public TextField getTxtModelo() {
        return txtModelo;
    }

    //@ skipesc
    public void setTxtModelo(TextField txtModelo) {
        this.txtModelo = txtModelo;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public TextField getTxtCor() {
        return txtCor;
    }

    //@ skipesc
    public void setTxtCor(TextField txtCor) {
        this.txtCor = txtCor;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public TextField getTxtpreco() {
        return txtpreco;
    }

    //@ skipesc
    public void setTxtpreco(TextField txtpreco) {
        this.txtpreco = txtpreco;
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
    public int getIdVeiculo() {
        return idVeiculo;
    }

    //@ assignable this.idVeiculo;
    //@ skipesc
    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    /*@ pure nullable @*/
    //@ skipesc
    public ComboBox<EstadoVeiculo> getComboBoxEstado() {
        return comboBoxEstado;
    }

    //@ skipesc
    public void setComboBoxEstado(ComboBox<EstadoVeiculo> comboBoxEstado) {
        this.comboBoxEstado = comboBoxEstado;
    }

    //@ skipesc
    public void limparDadosFormulario() {
        comboBoxEstado.setValue(null);
        txtAno.setText("");
        txtCor.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtpreco.setText("");
    }
    
}
