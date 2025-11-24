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
    private int id;
    //@ nullable
    private String marca;
    //@ nullable
    private String ano;
    //@ nullable
    private String modelo;
    //@ nullable
    private String cor;
    //@ nullable
    private String preco;
    //@ nullable
    private EstadoVeiculo estado;
    //@ nullable
    private StatusVeiculo status;

    public Veiculo() {
    }

    public Veiculo(String marca, String ano, String modelo, String cor, String preco, EstadoVeiculo estado) {
        this.marca = marca;
        this.ano = ano;
        this.modelo = modelo;
        this.cor = cor;
        this.preco = preco;
        this.estado = estado;
    }

    /*@ pure @*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*@ pure nullable @*/
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    /*@ pure nullable @*/
    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    /*@ pure nullable @*/
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /*@ pure nullable @*/
    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    /*@ pure nullable @*/
    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    /*@ pure nullable @*/
    public EstadoVeiculo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVeiculo estado) {
        this.estado = estado;
    }

    /*@ pure nullable @*/
    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    // Método toString
    /*@ pure nullable @*/
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
