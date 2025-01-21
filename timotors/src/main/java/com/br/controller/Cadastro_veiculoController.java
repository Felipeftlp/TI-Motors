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
    private ComboBox<EstadoVeiculo> comboBoxEstado;

    @FXML
    private TextField txtpreco;
    
    private boolean update;
    
    private int idVeiculo;
    
    @FXML
    @SuppressWarnings("unused")
    private void cadastrarVeiculo(ActionEvent event){
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();
        String ano = txtAno.getText();
        String cor = txtCor.getText();
        EstadoVeiculo estado = comboBoxEstado.getValue();
        String preco = txtpreco.getText();
        
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setAno(ano);
        veiculo.setCor(cor);
        veiculo.setEstado(estado);
        veiculo.setPreco(preco);
        
        VeiculoDAO dao = new VeiculoDAO();
               
        if (marca.equals("") || modelo.equals("") || ano.equals("") || cor.equals("") || preco.equals("") || estado == null) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Campos pendentes");
            alerta.setHeaderText("Campos pendentes");
            alerta.setContentText("Preencha todos os campos antes de prosseguir!");
                           
            alerta.showAndWait();
        } else {
            if (update) {
                veiculo.setId(idVeiculo);
                dao.update(veiculo);
    
                Alert alerta = new Alert(AlertType.CONFIRMATION);
                alerta.setTitle("Atualização de veículo");
                alerta.setHeaderText("Atualização de veículo");
                alerta.setContentText("veículo atualizado com sucesso!!");
                           
                alerta.showAndWait();
    
                fecharModal();
            } else {
                dao.create(veiculo);
    
                Alert alerta = new Alert(AlertType.CONFIRMATION);
                alerta.setTitle("Cadastro de veículo");
                alerta.setHeaderText("Cadastro de veículo");
                alerta.setContentText("veículo cadastrado com sucesso!!");
    
                alerta.showAndWait();
    
                limparDadosFormulario();
            }
        }
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
        // Adicionando itens ao ComboBox
        comboBoxEstado.getItems().addAll(EstadoVeiculo.NOVO, EstadoVeiculo.SEMINOVO, EstadoVeiculo.USADO);
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

    public ComboBox<EstadoVeiculo> getComboBoxEstado() {
        return comboBoxEstado;
    }

    public void setComboBoxEstado(ComboBox<EstadoVeiculo> comboBoxEstado) {
        this.comboBoxEstado = comboBoxEstado;
    }

    public void limparDadosFormulario() {
        comboBoxEstado.setValue(null);
        txtAno.setText("");
        txtCor.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtpreco.setText("");
    }
    
}
