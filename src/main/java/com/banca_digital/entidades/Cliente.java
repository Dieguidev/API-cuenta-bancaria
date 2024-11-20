package com.banca_digital.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;

    // Cliente tiene muchas cuentas bancarias
    @OneToMany(mappedBy = "cliente")
    private List<CuentaBancaria> cuentasBancarias;
}