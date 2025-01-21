package com.br.model;

import java.time.LocalDate;

public interface MetricasEmpresa {
    double CalcularSalario(Cargo cargo, int anosNaEmpresa);

    int CalcularAnosNaEmpresa(LocalDate dataAdmissao);
}
