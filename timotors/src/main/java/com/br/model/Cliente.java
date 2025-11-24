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
    //@ nullable
    private EstadoVeiculo interesse;
    //@ nullable
    private String endereco;

    public Cliente() {
        super();
    }

    //@ requires endereco != null;
    //@ requires nome != null && cpf != null && telefone != null && email != null;
    public Cliente(int id, String nome, String cpf, String telefone, String email, EstadoVeiculo interesse, 
                   /*@ non_null @*/ String endereco) {
        super(id, nome, cpf, telefone, email);
        this.interesse = interesse;
        this.endereco = endereco;
    }

    /*@ pure nullable @*/
    public EstadoVeiculo getInteresse() {
        return interesse;
    }

    public void setInteresse(EstadoVeiculo interesse) {
        this.interesse = interesse;
    }

    /*@ pure nullable @*/
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
