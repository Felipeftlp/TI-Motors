package com.br.model;

import java.time.LocalDate;

public interface MetricasEmpresa {
    
    /*@ pure @*/
    double CalcularSalario(Cargo cargo, int anosNaEmpresa);
    
    //@ assignable \nothing;
    int CalcularAnosNaEmpresa(/*@ nullable @*/ LocalDate dataAdmissao);
}