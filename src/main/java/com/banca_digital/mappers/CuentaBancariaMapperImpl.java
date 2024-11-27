package com.banca_digital.mappers;

import com.banca_digital.dtos.ClienteDTO;
import com.banca_digital.dtos.CuentaActualDTO;
import com.banca_digital.dtos.CuentaAhorroDTO;
import com.banca_digital.dtos.OperacionCuentaDTO;
import com.banca_digital.entidades.Cliente;
import com.banca_digital.entidades.CuentaActual;
import com.banca_digital.entidades.CuentaAhorro;
import com.banca_digital.entidades.OperacionCuenta;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CuentaBancariaMapperImpl {
    public ClienteDTO mapearDeCliente(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        BeanUtils.copyProperties(cliente, clienteDTO);
        return clienteDTO;
    }

    public Cliente mapearDeClienteDTO(ClienteDTO clienteDto) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDto, cliente);
        return cliente;
    }

    public CuentaAhorroDTO mapearDeCuentaAhorro(CuentaAhorro cuentaAhorro) {
        CuentaAhorroDTO cuentaAhorroDTO = new CuentaAhorroDTO();
        BeanUtils.copyProperties(cuentaAhorro, cuentaAhorroDTO);
        cuentaAhorroDTO.setClienteDTO(mapearDeCliente(cuentaAhorro.getCliente()));
        cuentaAhorroDTO.setTipo(cuentaAhorro.getClass().getSimpleName());
        return cuentaAhorroDTO;
    }

    public CuentaAhorro mapearDeCuentaAhorroDTO(CuentaAhorroDTO cuentaAhorroDTO) {
        CuentaAhorro cuentaAhorro = new CuentaAhorro();
        BeanUtils.copyProperties(cuentaAhorroDTO, cuentaAhorro);
        cuentaAhorro.setCliente(mapearDeClienteDTO(cuentaAhorroDTO.getClienteDTO()));
        return cuentaAhorro;
    }

    public CuentaActualDTO mapearDeCuentaActual(CuentaActual cuentaActual) {
        CuentaActualDTO cuentaActualDTO = new CuentaActualDTO();
        BeanUtils.copyProperties(cuentaActual, cuentaActualDTO);
        cuentaActualDTO.setClienteDTO(mapearDeCliente(cuentaActual.getCliente()));
        cuentaActualDTO.setTipo(cuentaActual.getClass().getSimpleName());
        return cuentaActualDTO;
    }

    public CuentaActual mapearDeCuentaActualDTO(CuentaActualDTO cuentaActualDTO) {
        CuentaActual cuentaActual = new CuentaActual();
        BeanUtils.copyProperties(cuentaActualDTO, cuentaActual);
        cuentaActual.setCliente(mapearDeClienteDTO(cuentaActualDTO.getClienteDTO()));
        return cuentaActual;
    }

    public OperacionCuentaDTO mapearDeOperacionCuenta(OperacionCuenta operacionCuenta) {
        OperacionCuentaDTO operacionCuentaDTO = new OperacionCuentaDTO();
        BeanUtils.copyProperties(operacionCuenta, operacionCuentaDTO);
        return operacionCuentaDTO;
    }
}
