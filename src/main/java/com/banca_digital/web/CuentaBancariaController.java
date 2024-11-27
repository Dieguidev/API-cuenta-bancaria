package com.banca_digital.web;

import com.banca_digital.dtos.CuentaBancariaDTO;
import com.banca_digital.dtos.OperacionCuentaDTO;
import com.banca_digital.excepciones.CuentaBancariaNotFoundException;
import com.banca_digital.servicios.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{cuentaId}/operaciones")
    public List<OperacionCuentaDTO> listarOperacionesDeUnaCuentaBancaria(@PathVariable String cuentaId) throws CuentaBancariaNotFoundException {
        return cuentaBancariaService.listarOperacionesCuenta(cuentaId);
    }

    @ExceptionHandler(CuentaBancariaNotFoundException.class)
    public ResponseEntity<String> handleCuentaBancariaNotFoundException(CuentaBancariaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
