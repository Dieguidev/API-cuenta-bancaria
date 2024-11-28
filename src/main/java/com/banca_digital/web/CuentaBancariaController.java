package com.banca_digital.web;

import com.banca_digital.dtos.*;
import com.banca_digital.excepciones.BalanceInsuficienteException;
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

    @GetMapping("/{cuentaId}/pageOperaciones")
    public HistorialCuentaDTO listarOperacionesDeUnaCuentaPaginada(@PathVariable String cuentaId, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size) throws CuentaBancariaNotFoundException {
        return cuentaBancariaService.getHistorialCuenta(cuentaId, page, size);
    }

    @PostMapping("/debito")
    public DebitoDTO realizarDebito(@RequestBody DebitoDTO debitoDTO) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        cuentaBancariaService.debit(debitoDTO.getCuentaId(), debitoDTO.getMonto(), debitoDTO.getDescripcion());
        return debitoDTO;
    }

    @PostMapping("/credito")
    public CreditoDTO realizarCredito(@RequestBody CreditoDTO creditoDTO) throws CuentaBancariaNotFoundException {
        cuentaBancariaService.credit(creditoDTO.getCuentaId(), creditoDTO.getMonto(), creditoDTO.getDescripcion());
        return creditoDTO;
    }

    @PostMapping("/transferencia")
    public void realizarTransferencia(@RequestBody TransferenciaRequestDTO transferenciaRequestDTO) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        cuentaBancariaService.transfer(transferenciaRequestDTO.getCuentaPropietario(), transferenciaRequestDTO.getCuentaDestinatario(),transferenciaRequestDTO.getMonto());
    }

    @ExceptionHandler(CuentaBancariaNotFoundException.class)
    public ResponseEntity<String> handleCuentaBancariaNotFoundException(CuentaBancariaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
