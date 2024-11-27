package com.banca_digital.repositorios;

import com.banca_digital.entidades.OperacionCuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperacionCuentaRepository extends JpaRepository<OperacionCuenta, Long> {
    List<OperacionCuenta> findByCuentaBancariaId(String cuentaId);
}
