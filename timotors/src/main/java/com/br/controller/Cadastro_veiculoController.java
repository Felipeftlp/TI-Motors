/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.br.dao.VeiculoDAO;
import com.br.model.Veiculo;

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
public class Cadastro_veiculoController implements Initializable {

    @FXML
    private Button Addveic;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtAno;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtCor;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtpreco;
    
    private boolean update;
    
    private int idVeiculo;
    
    @FXML
    private void cadastrarVeiculo(ActionEvent event){
        String marca =txtMarca.getText();
        String modelo = txtModelo.getText();
        String ano = txtAno.getText();
        String cor = txtCor.getText();
        String estado = txtEstado.getText();
        String preco = txtpreco.getText();
        
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setAno(ano);
        veiculo.setCor(cor);
        veiculo.setEstado(estado);
        veiculo.setPreco(preco);
        
        VeiculoDAO dao = new VeiculoDAO();
               
        if(update){
            veiculo.setId(idVeiculo);
            dao.update(veiculo);
        }else{
            dao.create(veiculo);
        }
        fecharModal(); 
    }
    
    public void fecharModal(){
        Stage stage = (Stage) Addveic.getScene().getWindow();
            
        stage.close();    
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public Button getAddveic() {
        return Addveic;
    }

    public void setAddveic(Button Addveic) {
        this.Addveic = Addveic;
    }

    public TextField getTxtMarca() {
        return txtMarca;
    }

    public void setTxtMarca(TextField txtMarca) {
        this.txtMarca = txtMarca;
    }

    public TextField getTxtAno() {
        return txtAno;
    }

    public void setTxtAno(TextField txtAno) {
        this.txtAno = txtAno;
    }

    public TextField getTxtModelo() {
        return txtModelo;
    }

    public void setTxtModelo(TextField txtModelo) {
        this.txtModelo = txtModelo;
    }

    public TextField getTxtCor() {
        return txtCor;
    }

    public void setTxtCor(TextField txtCor) {
        this.txtCor = txtCor;
    }

    public TextField getTxtpreco() {
        return txtpreco;
    }

    public void setTxtpreco(TextField txtpreco) {
        this.txtpreco = txtpreco;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public TextField getTxtEstado() {
        return txtEstado;
    }

    public void setTxtEstado(TextField txtEstado) {
        this.txtEstado = txtEstado;
    }
    
}
