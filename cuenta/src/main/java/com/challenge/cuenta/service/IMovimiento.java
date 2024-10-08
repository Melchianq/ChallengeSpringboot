package com.challenge.cuenta.service;

import java.util.Date;
import java.util.List;

import com.challenge.cuenta.error.LocalBadRequestException;
import com.challenge.cuenta.error.LocalNotFoundException;
import com.challenge.cuenta.model.entity.Cuenta;
import com.challenge.cuenta.model.entity.Movimiento;

public interface IMovimiento {
    Movimiento registrarMovimiento(Cuenta cuenta, Movimiento movimiento) throws LocalBadRequestException;
    Movimiento findById(Long id) throws LocalNotFoundException;
    void delete(Movimiento movimiento) throws LocalNotFoundException;
    List<Movimiento> obtenerMovimientosPorFecha(Cuenta cuenta, Date fechaInicio, Date fechaFin);
}
