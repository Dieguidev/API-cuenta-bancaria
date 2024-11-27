package com.banca_digital.web;

import com.banca_digital.dtos.CuentaBancariaDTO;
import com.banca_digital.excepciones.CuentaBancariaNotFoundException;
import com.banca_digital.servicios.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cuentas")
public class CuentaBancariaController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @GetMapping("/{cuentaId}")
    public CuentaBancariaDTO listarDatosDeUnaCuentaBancaria(@PathVariable String cuentaId) throws CuentaBancariaNotFoundException {
        return cuentaBancariaService.getCuentaBancaria(cuentaId);
    }

    @GetMapping("")
    public List<CuentaBancariaDTO> listarCuentasBancarias() {
        return cuentaBancariaService.listCuentasBancarias();
    }


}
