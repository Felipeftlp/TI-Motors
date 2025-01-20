package com.br.model;

import java.time.LocalDate;

public interface Salario {
    double CalcularSalario(Cargo cargo, int anosNaEmpresa);

    int CalcularAnosNaEmpresa(LocalDate dataAdmissao);
}
