package com.banca_digital.dtos;

import com.banca_digital.enums.EstadoCuenta;
import lombok.Data;

@Data
public class CuentaBancariaDTO {
    private String tipo;
    private EstadoCuenta estadoCuenta;
}
