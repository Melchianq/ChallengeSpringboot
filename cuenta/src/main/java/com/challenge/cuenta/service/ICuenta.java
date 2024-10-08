package com.challenge.cuenta.service;

import java.util.List;

import com.challenge.cuenta.error.LocalBadRequestException;
import com.challenge.cuenta.error.LocalNotFoundException;
import com.challenge.cuenta.model.entity.Cuenta;

public interface ICuenta {
    Cuenta save(Cuenta cuenta) throws LocalBadRequestException;
    Iterable<Cuenta> findAll();
    Cuenta findById(Long id) throws LocalNotFoundException;
    Cuenta findByNumeroCuenta(String numero) throws LocalNotFoundException;
    void delete(Cuenta cliente) throws LocalNotFoundException;
    List<Cuenta> findByIdCliente(Long id);
}
