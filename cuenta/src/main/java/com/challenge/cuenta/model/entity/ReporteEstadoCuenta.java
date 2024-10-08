package com.challenge.cuenta.model.entity;

import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@ToString
public class ReporteEstadoCuenta {

    private List<Cuenta> cuentas;
    private List<Movimiento> movimientos;

    public ReporteEstadoCuenta(List<Cuenta> cuentas, List<Movimiento> movimientos) {
        this.cuentas = cuentas;
        this.movimientos = movimientos;
    }
}
