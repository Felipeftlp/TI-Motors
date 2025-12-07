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
public class Veiculo {
    
    //@ spec_public
    private int id;
    
    //@ spec_public nullable
    private String marca;
    
    //@ spec_public nullable
    private String ano;
    
    //@ spec_public nullable
    private String modelo;
    
    //@ spec_public nullable
    private String cor;
    
    //@ spec_public nullable
    private String preco;
    
    //@ spec_public nullable
    private EstadoVeiculo estado;
    
    //@ spec_public nullable
    private StatusVeiculo status;

    /*@ 
      @ ensures id == 0;
      @ ensures marca == null;
      @ ensures ano == null;
      @ ensures modelo == null;
      @ ensures cor == null;
      @ ensures preco == null;
      @ ensures estado == null;
      @ ensures status == null;
      @*/
    public Veiculo() {
        this.id = 0;
        this.marca = null;
        this.ano = null;
        this.modelo = null;
        this.cor = null;
        this.preco = null;
        this.estado = null;
        this.status = null;
    }

    /*@ 
      @ ensures this.marca == marca;
      @ ensures this.ano == ano;
      @ ensures this.modelo == modelo;
      @ ensures this.cor == cor;
      @ ensures this.preco == preco;
      @ ensures this.estado == estado;
      @*/
    public Veiculo(String marca, String ano, String modelo, String cor, String preco, EstadoVeiculo estado, StatusVeiculo status) {
        this.marca = marca;
        this.ano = ano;
        this.modelo = modelo;
        this.cor = cor;
        this.preco = preco;
        this.estado = estado;
        this.status = status;
    }

    /*@ pure @*/
    public int getId() {
        return id;
    }

    //@ assignable this.id;
    public void setId(int id) {
        this.id = id;
    }

    /*@ pure nullable @*/
    public String getMarca() {
        return marca;
    }

    //@ assignable this.marca;
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /*@ pure nullable @*/
    public String getAno() {
        return ano;
    }

    //@ assignable this.ano;
    public void setAno(String ano) {
        this.ano = ano;
    }

    /*@ pure nullable @*/
    public String getModelo() {
        return modelo;
    }

    //@ assignable this.modelo;
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /*@ pure nullable @*/
    public String getCor() {
        return cor;
    }

    //@ assignable this.cor;
    public void setCor(String cor) {
        this.cor = cor;
    }

    /*@ pure nullable @*/
    public String getPreco() {
        return preco;
    }

    //@ assignable this.preco;
    public void setPreco(String preco) {
        this.preco = preco;
    }

    /*@ pure nullable @*/
    public EstadoVeiculo getEstado() {
        return estado;
    }

    //@ assignable this.estado;
    public void setEstado(EstadoVeiculo estado) {
        this.estado = estado;
    }

    /*@ pure nullable @*/
    public StatusVeiculo getStatus() {
        return status;
    }

    //@ assignable this.status;
    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    // Método toString
    //@ skipesc
    @Override
    public String toString() {
        return "Veiculo{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", ano='" + ano + '\'' +
                ", modelo='" + modelo + '\'' +
                ", preco='" + preco + '\'' +
                ", estado=" + estado +
                ", status=" + status +
                '}';
    }
}