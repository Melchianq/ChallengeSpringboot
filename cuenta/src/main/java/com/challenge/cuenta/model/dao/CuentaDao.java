package com.challenge.cuenta.model.dao;

import java.util.Optional;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.challenge.cuenta.model.entity.Cuenta;

public interface CuentaDao extends CrudRepository<Cuenta, Long>{

    Optional<Cuenta> findByNumeroCuenta(String numero);
    Optional<List<Cuenta>> findByIdCliente(Long id);
}
