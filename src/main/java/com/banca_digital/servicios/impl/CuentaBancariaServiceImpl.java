package com.banca_digital.servicios.impl;

import com.banca_digital.entidades.Cliente;
import com.banca_digital.entidades.CuentaAhorro;
import com.banca_digital.entidades.CuentaBancaria;
import com.banca_digital.excepciones.BalanceInsuficienteException;
import com.banca_digital.excepciones.ClienteNotFoundException;
import com.banca_digital.excepciones.CuentaBancariaNotFoundException;
import com.banca_digital.repositorios.ClienteRepository;
import com.banca_digital.repositorios.CuentaBancariaRepository;
import com.banca_digital.repositorios.OperacionCuentaRepository;
import com.banca_digital.servicios.CuentaBancariaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class CuentaBancariaServiceImpl implements CuentaBancariaService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    @Autowired
    private OperacionCuentaRepository operacionCuentaRepository;

    @Override
    public Cliente saveCliente(Cliente cliente) {
        log.info("Guardando un nuevo cliente");
        Cliente clienteBBDD = clienteRepository.save(cliente);
        return clienteBBDD;
    }

    @Override
    public CuentaBancaria saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException {
        return null;
    }

    @Override
    public CuentaAhorro saveCuentaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException {
        return null;
    }

    @Override
    public List<Cliente> listarClientes() {
        return List.of();
    }

    @Override
    public CuentaBancaria getCuentaBancaria(String id) throws CuentaBancariaNotFoundException {
        return null;
    }

    @Override
    public void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {

    }

    @Override
    public void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException {

    }

    @Override
    public void transfer(String cuentaIdPropietario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {

    }

    @Override
    public List<CuentaBancaria> listCuentasBancarias() {
        return List.of();
    }
}
