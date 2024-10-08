package com.challenge.cuenta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.cuenta.error.LocalBadRequestException;
import com.challenge.cuenta.error.LocalNotFoundException;
import com.challenge.cuenta.model.dao.CuentaDao;
import com.challenge.cuenta.model.entity.Cuenta;
import com.challenge.cuenta.service.ICuenta;

@Service
public class CuentaImpl implements ICuenta {

    @Autowired
    private CuentaDao cuentaDao;

    @Transactional
    @Override
    public Cuenta save(Cuenta cuenta)  throws LocalBadRequestException{
        return cuentaDao.save(cuenta);
    }

    @Transactional(readOnly = true)
    @Override
    public Cuenta findById(Long id)  throws LocalNotFoundException{
        return cuentaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Cuenta cuenta)  throws LocalNotFoundException{
        cuentaDao.delete(cuenta);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Cuenta> findAll() {
        return cuentaDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Cuenta findByNumeroCuenta(String numero) throws LocalNotFoundException {
        return cuentaDao.findByNumeroCuenta(numero).orElse(null);
    }

    @Transactional
    public void actualizarSaldo(Cuenta cuenta, Double nuevoSaldo) {
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaDao.save(cuenta);
    }

    @Override
    public List<Cuenta> findByIdCliente(Long id) {
        return cuentaDao.findByIdCliente(id).orElse(null);
    }

}
