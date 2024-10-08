package com.challenge.cuenta.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.cuenta.error.LocalBadRequestException;
import com.challenge.cuenta.error.LocalNotFoundException;
import com.challenge.cuenta.model.entity.Cuenta;
import com.challenge.cuenta.model.entity.Movimiento;
import com.challenge.cuenta.service.ICuenta;
import com.challenge.cuenta.service.IMovimiento;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1")
public class MovimientoController {

    @Autowired
    private ICuenta cuentaService;

    @Autowired
    private IMovimiento movimientoService;

    @PostMapping("movimientos")
    @ResponseStatus(HttpStatus.CREATED)
    public Movimiento create(@RequestBody Movimiento movimiento) throws LocalBadRequestException{
        Cuenta cuenta = cuentaService.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
        if (cuenta == null) {
            throw new LocalNotFoundException("Cuenta no encontrada");
        }

        return movimientoService.registrarMovimiento(cuenta, movimiento);
    }

    @PutMapping("movimientos")
    @ResponseStatus(HttpStatus.OK)
    public Movimiento update(Movimiento movimiento) throws LocalNotFoundException{
        Long id = movimiento.getIdMovimiento();
        Movimiento movimientoUpdate = movimientoService.findById(id);
        if (movimientoUpdate == null) {
            throw new LocalNotFoundException("El movimiento con el id: " + id + "  no existe");
        }

        Cuenta cuenta = cuentaService.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
        if (cuenta == null) {
            throw new LocalNotFoundException("Cuenta no encontrada");
        }

        return movimientoService.registrarMovimiento(cuenta, movimiento);
    }

    @DeleteMapping("movimientos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(Long id) throws LocalNotFoundException{
        Movimiento movimientoDelete = movimientoService.findById(id);
        if (movimientoDelete == null) {
            throw new LocalNotFoundException("El movimiento con el id: " + id + " no fue encontrado");
        }
        else{
            movimientoService.delete(movimientoDelete);
        }
    }
}
