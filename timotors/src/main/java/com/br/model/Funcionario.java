/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.model;

import java.util.Date;

/**
 *
 * @author felip
 */
public class Funcionario extends Pessoa implements Salario{
    private String cargo;
    private double salario;
    private String turno;
    private String departamento;
    private int anosExperiencia;
    private Date dataAdmissao;

    public Funcionario() {
        super();
    }

    public Funcionario(int id, String nome, String cpf, String telefone, String email, String cargo, 
                       double salario, String turno, String departamento, int anosExperiencia, Date dataAdmissao) {
        super(id, nome, cpf, telefone, email);
        this.cargo = cargo;
        this.salario = salario;
        this.turno = turno;
        this.departamento = departamento;
        this.anosExperiencia = anosExperiencia;
        this.dataAdmissao = dataAdmissao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(int anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    @Override
    public float CalcularSalario(int id, String cargo, int anosExperiencia) {
        float salarioBase = 3000.00f; // Definindo um valor base para o salário

        // Ajuste do salário com base no cargo
        switch (cargo.toLowerCase()) {
            case "analista":
                salarioBase += 1000;
                break;
            case "gerente":
                salarioBase += 3000;
                break;
            case "diretor":
                salarioBase += 5000;
                break;
            default:
                salarioBase += 500;
                break;
        }

        // Ajuste do salário com base nos anos de experiência
        salarioBase += anosExperiencia * 200;

        return salarioBase;
    }

    // Método toString
    @Override
    public String toString() {
        return "Funcionario{" +
                "cargo='" + cargo + '\'' +
                ", salario=" + salario +
                ", turno='" + turno + '\'' +
                ", departamento='" + departamento + '\'' +
                ", anosExperiencia=" + anosExperiencia +
                ", dataAdmissao=" + dataAdmissao +
                "} " + super.toString();
    }
}
