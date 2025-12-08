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

    /*@ public invariant anosNaEmpresa >= 0 && anosNaEmpresa < 100; @*/
    /*@ public invariant salario >= 0.00; @*/
    /*@ public invariant cargo != null; @*/

    //@ spec_public non_null
    private Cargo cargo;
    
    //@ spec_public
    private double salario;
    
    //@ spec_public
    private double comissao;
    
    //@ spec_public
    private int anosNaEmpresa;
    
    //@ spec_public nullable
    private LocalDate dataAdmissao;

    /*@ 
      @ ensures anosNaEmpresa == 0;
      @ ensures salario == 0.00;
      @ ensures cargo == Cargo.VENDEDOR;
      @*/
    public Funcionario() {
        super();
        this.anosNaEmpresa = 0;
        this.salario = 0.00;
        this.cargo = Cargo.VENDEDOR;
    }

    /*@ 
      @ requires cargo != null;
      @ requires anosNaEmpresa >= 0 && anosNaEmpresa < 100;
      @ requires salario >= 0.00;
      @ ensures this.cargo == cargo;
      @ ensures this.salario == salario;
      @ ensures this.anosNaEmpresa == anosNaEmpresa;
      @ ensures this.dataAdmissao == dataAdmissao;
      @*/
    public Funcionario(int id, String nome, String cpf, String telefone, String email, String endereco, Cargo cargo, 
                       double salario, int anosNaEmpresa, LocalDate dataAdmissao) {
        super(id, nome, cpf, telefone, email, endereco);
        this.cargo = cargo;
        this.salario = salario;
        this.anosNaEmpresa = anosNaEmpresa;
        this.dataAdmissao = dataAdmissao;
    }

    /*@ pure non_null @*/
    public Cargo getCargo() {
        return cargo;
    }

    /*@ 
      @ requires cargo != null;
      @ assignable this.cargo;
      @ ensures this.cargo == cargo;
      @*/
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    /*@ pure @*/
    public double getSalario() {
        return salario;
    }

    /*@ 
      @ assignable this.salario;
      @ ensures salario == \old(CalcularSalario(cargo, anosNaEmpresa));
      @*/
    public void setSalario() {
        this.salario = CalcularSalario(cargo, anosNaEmpresa);
    }

    /*@ 
      @ requires salario >= 3500.00;
      @ assignable this.salario;
      @*/
    public void setSalario(double salario) {
        this.salario = salario;
    }

    /*@ pure @*/
    public int getAnosNaEmpresa() {
        return anosNaEmpresa;
    }
    
    /*@ 
      @ assignable this.anosNaEmpresa;
      @*/
    public void setAnosNaEmpresa() {
        int resultado = CalcularAnosNaEmpresa(dataAdmissao);
        this.anosNaEmpresa = resultado;
    }

    /*@ 
      @ requires anosNaEmpresa >= 0 && anosNaEmpresa < 100;
      @ assignable this.anosNaEmpresa;
      @ ensures this.anosNaEmpresa == anosNaEmpresa;
      @*/
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

    /*@ also
      @ requires cargo != null;
      @ requires anosNaEmpresa >= 0;
      @ ensures \result >= 3500.00; 
      @ ensures \result == 3000.00 + (anosNaEmpresa * 200) + 
      @         (cargo == Cargo.ANALISTA ? 1000 : 
      @          cargo == Cargo.GERENTE ? 3000 : 
      @          cargo == Cargo.DIRETOR ? 5000 : 
      @          cargo == Cargo.VENDEDOR ? 500 : 0);
      @*/
    @Override
    public double CalcularSalario(Cargo cargo, int anosNaEmpresa) {
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
    public int CalcularAnosNaEmpresa(/*@ nullable @*/ LocalDate dataAdmissao) {
        if (dataAdmissao == null) {
            return 0; 
        }
        
        long tempo = calcularDiferencaAnosSystem(dataAdmissao);
        
        if (tempo < 0) return 0;
        if (tempo >= 100) return 99; 

        return (int) tempo;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @*/
    //@ skipesc
    private long calcularDiferencaAnosSystem(LocalDate dataAdmissao) {
        return ChronoUnit.YEARS.between(dataAdmissao, LocalDate.now());
    }

    //@ skipesc
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