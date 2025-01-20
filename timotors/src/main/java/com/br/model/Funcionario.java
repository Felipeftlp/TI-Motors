/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author felip
 */
public class Funcionario extends Pessoa implements Salario{
    private Cargo cargo;
    private double salario;
    private int anosNaEmpresa;
    private LocalDate dataAdmissao;

    public Funcionario() {
        super();
    }

    public Funcionario(int id, String nome, String cpf, String telefone, String email, Cargo cargo, 
                       double salario, int anosNaEmpresa, LocalDate dataAdmissao) {
        super(id, nome, cpf, telefone, email);
        this.cargo = cargo;
        this.salario = salario;
        this.anosNaEmpresa = anosNaEmpresa;
        this.dataAdmissao = dataAdmissao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario() {
        this.salario = CalcularSalario(cargo, anosNaEmpresa);
    }

    // Sobrecarga para definir o salário diretamente
    public void setSalario(String salario) {
        this.salario = Double.parseDouble(salario);
    }

    public int getAnosNaEmpresa() {
        return anosNaEmpresa;
    }

    public void setAnosNaEmpresa() {
        this.anosNaEmpresa = CalcularAnosNaEmpresa(dataAdmissao);
    }

    // Sobrecarga para definir anos de experiência diretamente
    public void setAnosNaEmpresa(int anosNaEmpresa) {
        this.anosNaEmpresa = anosNaEmpresa;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    @Override
    public double CalcularSalario(Cargo cargo, int anosNaEmpresa) {
        double salarioBase = 3000.00f; // Definindo um valor base para o salário

        // Ajuste do salário com base no cargo
        switch (cargo) {
            case ANALISTA:
                salarioBase += 1000;
                break;
            case GERENTE:
                salarioBase += 3000;
                break;
            case DIRETOR:
                salarioBase += 5000;
                break;
            case VENDEDOR:
                salarioBase += 500;
                break;
        }

        // Ajuste do salário com base nos anos de experiência
        salarioBase += anosNaEmpresa * 200;

        return salarioBase;
    }

    @Override
    public int CalcularAnosNaEmpresa(LocalDate dataAdmissao) {
        
        double tempoNaEmpresa = ChronoUnit.YEARS.between(dataAdmissao, LocalDate.now());

        return (int) tempoNaEmpresa;
    }

    // Método toString
    @Override
    public String toString() {
        return "Funcionario{" +
                "cargo='" + cargo + '\'' +
                ", salario=" + salario +
                ", anosExperiencia=" + anosNaEmpresa +
                ", dataAdmissao=" + dataAdmissao +
                "} " + super.toString();
    }
}
