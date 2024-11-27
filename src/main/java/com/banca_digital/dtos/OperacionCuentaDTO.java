package com.banca_digital.dtos;

import com.banca_digital.entidades.CuentaBancaria;
import com.banca_digital.enums.TipoOperacion;

import lombok.Data;

import java.util.Date;

@Data
public class OperacionCuentaDTO {
    private Long id;

    private Date fechaOperacion;

    private double monto;

    private String descripcion;

    private TipoOperacion tipoOperacion;


}
