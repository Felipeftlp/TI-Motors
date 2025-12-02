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
public class Cliente extends Pessoa {
    
    //@ spec_public nullable
    private EstadoVeiculo interesse;
    
    //@ spec_public nullable
    private String endereco;

    /*@ 
      @ ensures interesse == null;
      @ ensures endereco == null;
      @*/
    public Cliente() {
        super();
        this.interesse = null;
        this.endereco = null;
    }

    //@ skipesc
    public Cliente(int id, String nome, String cpf, String telefone, String email, EstadoVeiculo interesse, String endereco) {
        super(id, nome, cpf, telefone, email);
        this.interesse = interesse;
        this.endereco = endereco;
    }

    /*@ pure nullable @*/
    public EstadoVeiculo getInteresse() {
        return interesse;
    }

    //@ assignable this.interesse;
    public void setInteresse(EstadoVeiculo interesse) {
        this.interesse = interesse;
    }

    /*@ pure nullable @*/
    public String getEndereco() {
        return endereco;
    }

    //@ assignable this.endereco;
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    //@ skipesc
    @Override
    public String toString() {
        return "Cliente{" +
                "interesse='" + interesse + '\'' +
                ", endereco='" + endereco + '\'' +
                "} " + super.toString();
    }
}