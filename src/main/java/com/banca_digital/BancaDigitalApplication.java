package com.banca_digital;

import com.banca_digital.dtos.ClienteDTO;
import com.banca_digital.entidades.*;
import com.banca_digital.excepciones.ClienteNotFoundException;
import com.banca_digital.servicios.BancoService;
import com.banca_digital.servicios.CuentaBancariaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

                    List<CuentaBancaria> cuentasBancarias = cuentaBancariaService.listCuentasBancarias();
                    for(CuentaBancaria cuentaBancaria : cuentasBancarias) {
                        for (int i = 0; i < 10; i++) {
                            try {

                            cuentaBancariaService.credit(cuentaBancaria.getId(), 10000+Math.random()*120000, "Credito");
                            cuentaBancariaService.debit(cuentaBancaria.getId(), 1000+Math.random()*90000, "Debito");
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
