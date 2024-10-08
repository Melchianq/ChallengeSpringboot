package com.challenge.cuenta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.cuenta.error.LocalBadRequestException;
import com.challenge.cuenta.error.LocalNotFoundException;
import com.challenge.cuenta.model.entity.Cuenta;
import com.challenge.cuenta.service.ICuenta;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
public class CuentaController {

    @Autowired
    private ICuenta cuentaService;

    @PostMapping("cuentas")
    @ResponseStatus(HttpStatus.CREATED)
    public Cuenta create(@RequestBody Cuenta cuenta) throws LocalBadRequestException, LocalNotFoundException{
        String numeroCuenta = cuenta.getNumeroCuenta();
        if(numeroCuenta == null || numeroCuenta.isBlank() || numeroCuenta.equalsIgnoreCase("null")){
            throw new LocalNotFoundException("El número de cuenta no puede ser nulo");
        }
        else{
            Cuenta cuentaConsulta =  cuentaService.findByNumeroCuenta(numeroCuenta);
            if (cuentaConsulta == null) {
                return cuentaService.save(cuenta);
            }
            else{
                throw new LocalNotFoundException("La cuenta ya existe");
            }
            
        }
    }

    @PutMapping("cuentas")
    @ResponseStatus(HttpStatus.OK)
    public Cuenta update(Cuenta cuenta) throws LocalNotFoundException{
        String numeroCuenta = cuenta.getNumeroCuenta();
        Cuenta cuentaUpdate = cuentaService.findByNumeroCuenta(numeroCuenta);
        if (cuentaUpdate == null) {
            throw new LocalNotFoundException("La cuenta con el id: " + numeroCuenta + "  no fue encontrado");
        }
        else{
            return cuentaService.save(cuenta);
        }
    }

    @DeleteMapping("cuentas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(Long id) throws LocalNotFoundException{
        Cuenta cuentaDelete = cuentaService.findById(id);
        if (cuentaDelete == null) {
            throw new LocalNotFoundException("La cuenta con el id: " + id + " no fue encontrado");
        }
        else{
            cuentaService.delete(cuentaDelete);
        }
    }

    @GetMapping("cuentas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cuenta showById(Long id) throws LocalNotFoundException{
            Cuenta cuenta =  cuentaService.findById(id);
            if (cuenta == null) {
                throw new LocalNotFoundException("La cuenta con el id: " + id + " no fue encontrado");
            }
            else{
                return cuenta;
            }
    }

    @GetMapping("cuentas")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Cuenta> showAll() {
        Iterable<Cuenta> cuentas =  cuentaService.findAll();
        return cuentas;
    }
}
