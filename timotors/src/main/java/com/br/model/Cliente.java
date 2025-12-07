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

    /*@ 
      @ ensures interesse == null;
      @ ensures endereco == null;
      @*/
    public Cliente() {
        super();
        this.interesse = null;
    }

    /*@
      @ requires interesse != null && endereco != null && endereco.length() >= 0;
      @ ensures this.interesse == interesse;
      @ ensures this.endereco == endereco;
      @*/
    public Cliente(int id, String nome, String cpf, String telefone, String email, EstadoVeiculo interesse, String endereco) {
        super(id, nome, cpf, telefone, email, endereco);
        this.interesse = interesse;
    }

    /*@ pure nullable @*/
    public EstadoVeiculo getInteresse() {
        return interesse;
    }

    //@ assignable this.interesse;
    public void setInteresse(EstadoVeiculo interesse) {
        this.interesse = interesse;
    }
    
    //@ skipesc
    @Override
    public String toString() {
        return "Cliente{" +
                "interesse='" + interesse + '\'' +
                "} " + super.toString();
    }
}