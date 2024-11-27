package com.banca_digital.entidades;

import com.banca_digital.enums.EstadoCuenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
// InheritanceType.SINGLE_TABLE: Una sola tabla para todas las subclases
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO", length = 4)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaBancaria {
    @Id
    private String id;

    private double balance;

    private Date fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta = EstadoCuenta.ACTIVADA;

    // Cuenta bancaria pertenece a un cliente
    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "cuentaBancaria", fetch = FetchType.LAZY)
    private List<OperacionCuenta> operacionCuenta;
}
