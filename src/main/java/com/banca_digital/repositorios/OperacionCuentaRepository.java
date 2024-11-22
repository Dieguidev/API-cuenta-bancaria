package com.banca_digital.repositorios;

import com.banca_digital.entidades.OperacionCuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacionCuentaRepository extends JpaRepository<OperacionCuenta, Long> {
}
