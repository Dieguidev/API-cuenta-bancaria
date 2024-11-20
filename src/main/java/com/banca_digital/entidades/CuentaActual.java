package com.banca_digital.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DiscriminatorValue("CA")  // Cuenta actual
@NoArgsConstructor
@AllArgsConstructor
public class CuentaActual extends CuentaBancaria {
    private double sobregiro;

}
