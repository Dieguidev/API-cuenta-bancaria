package com.banca_digital;

import com.banca_digital.dtos.ClienteDTO;
import com.banca_digital.dtos.CuentaActualDTO;
import com.banca_digital.dtos.CuentaAhorroDTO;
import com.banca_digital.dtos.CuentaBancariaDTO;
import com.banca_digital.entidades.*;
import com.banca_digital.excepciones.ClienteNotFoundException;
import com.banca_digital.servicios.BancoService;
import com.banca_digital.servicios.CuentaBancariaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class BancaDigitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(BancaDigitalApplication.class, args);
    }

    //    @Bean
    CommandLineRunner commandLineRunner(BancoService bancoService) {
        return args -> {
            bancoService.consultar();
        };
    }

//    @Bean
    CommandLineRunner start(CuentaBancariaService cuentaBancariaService) {
        return args -> {
            Stream.of("Juan", "Pedro", "Maria", "Ana").forEach(nombre -> {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setNombre(nombre);
                cliente.setEmail(nombre + "@gmail.com");
                cuentaBancariaService.saveCliente(cliente);
            });

            cuentaBancariaService.listarClientes().forEach(cliente -> {
                try {
                    cuentaBancariaService.saveCuentaBancariaActual(Math.random() * 90000, 9000, cliente.getId());
                    cuentaBancariaService.saveCuentaAhorro(Math.random() * 90000, 0.05, cliente.getId());

                    List<CuentaBancariaDTO> cuentasBancarias = cuentaBancariaService.listCuentasBancarias();
                    for (CuentaBancariaDTO cuentaBancaria : cuentasBancarias) {
                        for (int i = 0; i < 10; i++) {

                            String cuentaId;

                            if (cuentaBancaria instanceof CuentaAhorroDTO) {
                                cuentaId = ((CuentaAhorroDTO) cuentaBancaria).getId();
                            } else {
                                cuentaId = ((CuentaActualDTO) cuentaBancaria).getId();
                            }

                            try {

                                cuentaBancariaService.credit(cuentaId, 10000 + Math.random() * 120000, "Credito");
                                cuentaBancariaService.debit(cuentaId, 1000 + Math.random() * 90000, "Debito");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (ClienteNotFoundException e) {
                    e.printStackTrace();
                }
            });
        };
    }
}
