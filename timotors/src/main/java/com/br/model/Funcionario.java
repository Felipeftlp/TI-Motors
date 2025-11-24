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
public class Funcionario extends Pessoa implements MetricasEmpresa {
    //@ nullable
    private Cargo cargo;
    private double salario;
    private double comissao;
    

    //@ public invariant anosNaEmpresa >= 0 && anosNaEmpresa < 100;
    private /*@ spec_public @*/ int anosNaEmpresa;
    
    //@ nullable
    private LocalDate dataAdmissao;

    //@ ensures anosNaEmpresa == 0;
    public Funcionario() {
        super();
        this.anosNaEmpresa = 0;
    }
    //@ requires anosNaEmpresa >= 0 && anosNaEmpresa < 100;
    public Funcionario(int id, String nome, String cpf, String telefone, String email, Cargo cargo, 
                       double salario, int anosNaEmpresa, LocalDate dataAdmissao) {
        super(id, nome, cpf, telefone, email);
        this.cargo = cargo;
        this.salario = salario;
        this.anosNaEmpresa = anosNaEmpresa;
        this.dataAdmissao = dataAdmissao;
    }

    /*@ pure nullable @*/
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    /*@ pure @*/
    public double getSalario() {
        return salario;
    }

    public void setSalario() {
        this.salario = CalcularSalario(cargo, anosNaEmpresa);
    }

    public void setSalario(String salario) {
        this.salario = Double.parseDouble(salario);
    }

    /*@ pure @*/
    public int getAnosNaEmpresa() {
        return anosNaEmpresa;
    }
    
    //@ ensures anosNaEmpresa >= 0 && anosNaEmpresa < 100;
    public void setAnosNaEmpresa() {
        int resultado = CalcularAnosNaEmpresa(dataAdmissao);
        this.anosNaEmpresa = resultado;
    }

    //@ requires anosNaEmpresa >= 0 && anosNaEmpresa < 100;
    //@ ensures this.anosNaEmpresa == anosNaEmpresa;
    public void setAnosNaEmpresa(int anosNaEmpresa) {
        this.anosNaEmpresa = anosNaEmpresa;
    }

    /*@ pure nullable @*/
    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    /*@ pure @*/
    public double getComissao() {
        return comissao;
    }

    public void setComissao(double comissao) {
        this.comissao = comissao;
    }

    @Override
    public double CalcularSalario(/*@ nullable @*/ Cargo cargo, int anosNaEmpresa) {
        double salarioBase = 3000.00f; 
        if (cargo != null) {
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
        }
        salarioBase += (long) anosNaEmpresa * 200;

        return salarioBase;
    }

    /*@ also
      @ ensures \result >= 0 && \result < 100;
      @*/
    @Override
    public int CalcularAnosNaEmpresa(/*@ nullable @*/LocalDate dataAdmissao) {
        //@ assume anosNaEmpresa >= 0 && anosNaEmpresa < 100;
        if (dataAdmissao == null) {
            return 0; 
        }
        
        long tempo = ChronoUnit.YEARS.between(dataAdmissao, LocalDate.now());
        
        if (tempo < 0) return 0;
        if (tempo >= 100) return 99; 

        return (int) tempo;
    }
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
