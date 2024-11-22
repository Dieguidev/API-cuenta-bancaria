package com.banca_digital.servicios;

import com.banca_digital.entidades.Cliente;
import com.banca_digital.entidades.CuentaAhorro;
import com.banca_digital.entidades.CuentaBancaria;
import com.banca_digital.excepciones.BalanceInsuficienteException;
import com.banca_digital.excepciones.ClienteNotFoundException;
import com.banca_digital.excepciones.CuentaBancariaNotFoundException;

import java.util.List;

public interface CuentaBancariaService {
    Cliente saveCliente(Cliente cliente);
    CuentaBancaria saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException;
    CuentaAhorro saveCuentaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException;
    List<Cliente> listarClientes();
    CuentaBancaria getCuentaBancaria(String id) throws CuentaBancariaNotFoundException;
    void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;
    void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException;
    void transfer(String cuentaIdPropietario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;
    List<CuentaBancaria> listCuentasBancarias();
}
