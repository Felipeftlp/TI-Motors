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
    private String marca;
    private String ano;
    private String modelo;
    private String cor;
    private String preco;
    private EstadoVeiculo estado;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
    
    
    public EstadoVeiculo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVeiculo estado) {
        this.estado = estado;
    }

    // Método toString
    @Override
    public String toString() {
        return "Veiculo{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", ano='" + ano + '\'' +
                ", modelo='" + modelo + '\'' +
                ", cor='" + cor + '\'' +
                ", preco='" + preco + '\'' +
                ", estado=" + estado +
                '}';
    }
}
