package com.banca_digital.web;

import com.banca_digital.dtos.ClienteDTO;

import com.banca_digital.excepciones.ClienteNotFoundException;
import com.banca_digital.servicios.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @GetMapping("")
    public List<ClienteDTO> listarClientes() {
        return cuentaBancariaService.listarClientes();
    }

    @GetMapping("/{clienteId}")
    public ClienteDTO traerDatosCliente(@PathVariable Long clienteId) throws ClienteNotFoundException {
        return cuentaBancariaService.getCliente(clienteId);
    }

    @PostMapping("")
    public ClienteDTO guardarCliente(@RequestBody ClienteDTO clienteDTO) {
        return cuentaBancariaService.saveCliente(clienteDTO);
    }

    @PutMapping("/{clienteId}")
    public ClienteDTO actualizarCliente(@PathVariable Long clienteId, @RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setId(clienteId);
        return cuentaBancariaService.updateCliente(clienteDTO);
    }

    @DeleteMapping("/{clienteId}")
    public void eliminarCliente(@PathVariable Long clienteId) {
        cuentaBancariaService.deleteCliente(clienteId);
    }
}
