package com.banca_digital.web;

import com.banca_digital.entidades.Cliente;
import com.banca_digital.servicios.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @GetMapping("")
    public List<Cliente> listarClientes(){
        return cuentaBancariaService.listarClientes();
    }
}
