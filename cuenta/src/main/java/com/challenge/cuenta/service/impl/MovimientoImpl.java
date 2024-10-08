package com.challenge.cuenta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.cuenta.error.LocalBadRequestException;
import com.challenge.cuenta.error.LocalNotFoundException;
import com.challenge.cuenta.model.dao.MovimientoDao;
import com.challenge.cuenta.model.entity.Cuenta;
import com.challenge.cuenta.model.entity.Movimiento;
import com.challenge.cuenta.service.IMovimiento;
import java.util.Date;

@Service
public class MovimientoImpl implements IMovimiento {


    @Autowired
    private MovimientoDao movimientoDao;

    @Transactional
    @Override
    public Movimiento registrarMovimiento(Cuenta cuenta, Movimiento movimiento)  throws LocalBadRequestException{
        String tipoMovimiento = movimiento.getTipoMovimiento();
        Double nuevoSaldo = 0d;
        if (tipoMovimiento.equals("Debito")) {
            nuevoSaldo = cuenta.getSaldoInicial() - movimiento.getValor();
        }
        else{
            nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();
        }
  
        if (nuevoSaldo < 0 && tipoMovimiento.equals("Debito")) {
            throw new LocalNotFoundException("Saldo no disponible");
        }

        cuenta.setSaldoInicial(nuevoSaldo);

        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);

        return movimientoDao.save(movimiento);
    }

    @Transactional(readOnly = true)
    @Override
    public Movimiento findById(Long id)  throws LocalNotFoundException{
        return movimientoDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Movimiento movimiento)  throws LocalNotFoundException{
        movimientoDao.delete(movimiento);
    }


    @Transactional(readOnly = true)
    public java.util.List<Movimiento> obtenerMovimientosPorFecha(Cuenta cuenta, Date fechaInicio, Date fechaFin) {
        return movimientoDao.findByCuentaAndFechaBetween(cuenta, fechaInicio, fechaFin);
    }
}
