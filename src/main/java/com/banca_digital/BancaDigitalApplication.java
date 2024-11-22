package com.banca_digital;

import com.banca_digital.entidades.Cliente;
import com.banca_digital.entidades.CuentaActual;
import com.banca_digital.entidades.CuentaAhorro;
import com.banca_digital.entidades.OperacionCuenta;
import com.banca_digital.enums.EstadoCuenta;
import com.banca_digital.enums.TipoOperacion;
import com.banca_digital.repositorios.ClienteRepository;
import com.banca_digital.repositorios.CuentaBancariaRepository;
import com.banca_digital.repositorios.OperacionCuentaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BancaDigitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(BancaDigitalApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ClienteRepository clienteRepository, CuentaBancariaRepository cuentaBancariaRepository, OperacionCuentaRepository operacionCuentaRepository) {
        return args -> {
            Stream.of("Juan", "Pedro", "Maria", "Ana").forEach(nombre -> {
                Cliente cliente = new Cliente();
                cliente.setNombre(nombre);
                cliente.setEmail(nombre + "@gmail.com");
                clienteRepository.save(cliente);
            });

            //le asignamos una cuenta bancaria a cada cliente
            clienteRepository.findAll().forEach((cliente -> {
                CuentaActual cuentaActual = new CuentaActual();
                cuentaActual.setId(UUID.randomUUID().toString());
                cuentaActual.setBalance(Math.random()*9000);
                cuentaActual.setFechaCreacion(new Date());
                cuentaActual.setEstadoCuenta((EstadoCuenta.CREADA));
                cuentaActual.setCliente(cliente);
                cuentaActual.setSobregiro(9000);
                cuentaBancariaRepository.save(cuentaActual);

                CuentaAhorro cuentaAhorro= new CuentaAhorro();
                cuentaAhorro.setId(UUID.randomUUID().toString());
                cuentaAhorro.setBalance(Math.random()*9000);
                cuentaAhorro.setFechaCreacion(new Date());
                cuentaAhorro.setEstadoCuenta((EstadoCuenta.CREADA));
                cuentaAhorro.setCliente(cliente);
                cuentaAhorro.setTasaDeInteres(5.5);
                cuentaBancariaRepository.save(cuentaAhorro);
            }));

            //agregamos operaciones a las cuentas
            cuentaBancariaRepository.findAll().forEach(cuentaBancaria -> {
                for (int i = 0; i < 10; i++) {
                    OperacionCuenta operacionCuenta = new OperacionCuenta();
                    operacionCuenta.setFechaOperacion(new Date());
                    operacionCuenta.setMonto(Math.random()*12000);
                    operacionCuenta.setTipoOperacion(Math.random() > 0.5 ? TipoOperacion.DEBITO : TipoOperacion.CREDITO);
                    operacionCuenta.setCuentaBancaria(cuentaBancaria);
                    operacionCuentaRepository.save(operacionCuenta);
                }
            });
        };
    }
}
