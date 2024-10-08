package com.challenge.cuenta.model.dao;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import com.challenge.cuenta.model.entity.Cuenta;
import com.challenge.cuenta.model.entity.Movimiento;

public interface MovimientoDao extends CrudRepository<Movimiento, Long>{

    java.util.List<Movimiento> findByCuentaAndFechaBetween(Cuenta cuenta, Date fechaInicio, Date fechaFin);
}

