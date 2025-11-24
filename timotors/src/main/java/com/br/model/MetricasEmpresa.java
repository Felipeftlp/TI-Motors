package com.br.model;

import java.time.LocalDate;

public interface MetricasEmpresa {
    double CalcularSalario(/*@ nullable @*/ Cargo cargo, int anosNaEmpresa);

    int CalcularAnosNaEmpresa(/*@ nullable @*/ LocalDate dataAdmissao);
}
