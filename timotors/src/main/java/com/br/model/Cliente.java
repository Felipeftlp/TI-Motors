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
    
    private EstadoVeiculo interesse;
    private String endereco;

    public Cliente() {
        super();
    }

    public Cliente(int id, String nome, String cpf, String telefone, String email, EstadoVeiculo interesse, 
                   String endereco) {
        super(id, nome, cpf, telefone, email);
        this.interesse = interesse;
        this.endereco = endereco;
    }

    public EstadoVeiculo getInteresse() {
        return interesse;
    }

    public void setInteresse(EstadoVeiculo interesse) {
        this.interesse = interesse;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "interesse='" + interesse + '\'' +
                ", endereco='" + endereco + '\'' +
                "} " + super.toString();
    }
    
}
