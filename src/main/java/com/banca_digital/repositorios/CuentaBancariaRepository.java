package com.banca_digital.repositorios;

import com.banca_digital.entidades.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, String> {

}
