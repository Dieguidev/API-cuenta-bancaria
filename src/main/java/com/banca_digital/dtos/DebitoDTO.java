package com.banca_digital.dtos;

import lombok.Data;

@Data
public class DebitoDTO {
    private String cuentaId;
    private double monto;
    private String descripcion;
}