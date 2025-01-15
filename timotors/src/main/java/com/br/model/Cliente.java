/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.model;

/**
 *
 * @author felip
 */
public class Cliente extends Pessoa{
    
    private String interesse;
    private String endereco;
    private String historicoCompras;

    public Cliente() {
        super();
    }

    public Cliente(int id, String nome, String cpf, String telefone, String email, String interesse, 
                   String endereco, String historicoCompras) {
        super(id, nome, cpf, telefone, email);
        this.interesse = interesse;
        this.endereco = endereco;
        this.historicoCompras = historicoCompras;
    }

    public String getInteresse() {
        return interesse;
    }

    public void setInteresse(String interesse) {
        this.interesse = interesse;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getHistoricoCompras() {
        return historicoCompras;
    }

    public void setHistoricoCompras(String historicoCompras) {
        this.historicoCompras = historicoCompras;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "interesse='" + interesse + '\'' +
                ", endereco='" + endereco + '\'' +
                ", historicoCompras='" + historicoCompras + '\'' +
                "} " + super.toString();
    }
    
}
