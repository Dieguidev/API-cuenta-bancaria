package com.banca_digital.servicios.impl;

import com.banca_digital.dtos.*;
import com.banca_digital.entidades.*;
import com.banca_digital.enums.TipoOperacion;
import com.banca_digital.excepciones.BalanceInsuficienteException;
import com.banca_digital.excepciones.ClienteNotFoundException;
import com.banca_digital.excepciones.CuentaBancariaNotFoundException;
import com.banca_digital.mappers.CuentaBancariaMapperImpl;
import com.banca_digital.repositorios.ClienteRepository;
import com.banca_digital.repositorios.CuentaBancariaRepository;
import com.banca_digital.repositorios.OperacionCuentaRepository;
import com.banca_digital.servicios.CuentaBancariaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    private CuentaBancariaMapperImpl cuentaBancariaMapper;

    @Override
    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        log.info("Guardando un nuevo cliente");
        Cliente cliente = cuentaBancariaMapper.mapearDeClienteDTO(clienteDTO);
        Cliente clienteBBDD = clienteRepository.save(cliente);
        return cuentaBancariaMapper.mapearDeCliente(clienteBBDD);
    }

    @Override
    public ClienteDTO getCliente(Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));
        return cuentaBancariaMapper.mapearDeCliente(cliente);
    }

    @Override
    public ClienteDTO updateCliente(ClienteDTO clienteDTO) {
        log.info("actualizando Cliente");
        Cliente cliente = cuentaBancariaMapper.mapearDeClienteDTO(clienteDTO);
        Cliente clienteBBDD = clienteRepository.save(cliente);
        return cuentaBancariaMapper.mapearDeCliente(clienteBBDD);
    }

    @Override
    public List<ClienteDTO> searchClientes(String keyword) {
        List<Cliente> clientes = clienteRepository.searchClientes(keyword);
        List <ClienteDTO> clientesDTO = clientes.stream()
                .map(cliente -> cuentaBancariaMapper.mapearDeCliente(cliente))
                .collect(Collectors.toList());
        return clientesDTO;
    }

    @Override
    public void deleteCliente(Long clienteId) {
        clienteRepository.deleteById(clienteId);
    }

    @Override
    public CuentaActualDTO saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente no encontrado");
        }
        CuentaActual cuentaActual = new CuentaActual();
        cuentaActual.setId(UUID.randomUUID().toString());
        cuentaActual.setFechaCreacion(new Date());
        cuentaActual.setBalance(balanceInicial);
        cuentaActual.setSobregiro(sobregiro);
        cuentaActual.setCliente(cliente);

        CuentaActual cuentaActualBBDD = cuentaBancariaRepository.save(cuentaActual);
        return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActualBBDD);

    }

    @Override
    public CuentaAhorroDTO saveCuentaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente no encontrado");
        }
        CuentaAhorro cuentaAhorro = new CuentaAhorro();
        cuentaAhorro.setId(UUID.randomUUID().toString());
        cuentaAhorro.setFechaCreacion(new Date());
        cuentaAhorro.setBalance(balanceInicial);
        cuentaAhorro.setTasaDeInteres(tasaInteres);
        cuentaAhorro.setCliente(cliente);

        CuentaAhorro cuentaAhorroBBDD = cuentaBancariaRepository.save(cuentaAhorro);
        return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorroBBDD);
    }

    @Override
    public List<ClienteDTO> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clienteDTOS = clientes.stream()
                .map(cliente -> cuentaBancariaMapper.mapearDeCliente(cliente))
                .collect(Collectors.toList());
        return clienteDTOS;
    }

    @Override
    public CuentaBancariaDTO getCuentaBancaria(String id) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(id)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));
        System.out.println(cuentaBancaria.getEstadoCuenta());
        if (cuentaBancaria instanceof CuentaAhorro) {
            return cuentaBancariaMapper.mapearDeCuentaAhorro((CuentaAhorro) cuentaBancaria);
        } else {
            return cuentaBancariaMapper.mapearDeCuentaActual((CuentaActual) cuentaBancaria);
        }
    }

    @Override
    public void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));

        if (cuentaBancaria.getBalance() < monto) {
            throw new BalanceInsuficienteException("Saldo insuficiente");
        }

        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.DEBITO);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setMonto(monto);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setCuentaBancaria(cuentaBancaria);

        operacionCuentaRepository.save(operacionCuenta);

        cuentaBancaria.setBalance(cuentaBancaria.getBalance() - monto);
        cuentaBancariaRepository.save(cuentaBancaria);
    }

    @Override
    public void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));

        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.CREDITO);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setMonto(monto);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setCuentaBancaria(cuentaBancaria);

        operacionCuentaRepository.save(operacionCuenta);

        cuentaBancaria.setBalance(cuentaBancaria.getBalance() + monto);
        cuentaBancariaRepository.save(cuentaBancaria);
    }

    @Override
    public void transfer(String cuentaIdPropietario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        debit(cuentaIdPropietario, monto, "Transferencia a " + cuentaIdDestinatario);
        credit(cuentaIdDestinatario, monto, "Transferencia de " + cuentaIdPropietario);
    }

    @Override
    public List<CuentaBancariaDTO> listCuentasBancarias() {
        List<CuentaBancaria> cuentas = cuentaBancariaRepository.findAll();
        List<CuentaBancariaDTO> cuentaBancariaDtos = cuentas.stream()
                .map(cuenta ->
                    cuenta instanceof CuentaAhorro ?
                        cuentaBancariaMapper.mapearDeCuentaAhorro((CuentaAhorro) cuenta) :
                        cuentaBancariaMapper.mapearDeCuentaActual((CuentaActual) cuenta)
                )
                .collect(Collectors.toList());
        return cuentaBancariaDtos;
    }

    @Override
    public List<OperacionCuentaDTO> listarOperacionesCuenta(String cuentaId) throws CuentaBancariaNotFoundException {
        cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));
        List<OperacionCuenta> operacionesCuentas = operacionCuentaRepository.findByCuentaBancariaId(cuentaId);
        List<OperacionCuentaDTO> operacionCuentaDTOS = operacionesCuentas.stream()
                .map(operacioncuenta -> cuentaBancariaMapper.mapearDeOperacionCuenta(operacioncuenta))
                .collect(Collectors.toList());
        return operacionCuentaDTOS;
    }

    @Override
    public HistorialCuentaDTO getHistorialCuenta(String cuentaId, int page, int size) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId).orElse(null);
        if (cuentaBancaria == null) {
            throw new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada");
        }

        Page<OperacionCuenta> operacionesCuenta = operacionCuentaRepository.findByCuentaBancariaId(cuentaId, PageRequest.of(page,size));
        HistorialCuentaDTO historialCuentaDTO = new HistorialCuentaDTO();
        List<OperacionCuentaDTO> operacionCuentaDTOS = operacionesCuenta.getContent().stream()
                .map(operacionCuenta -> cuentaBancariaMapper.mapearDeOperacionCuenta(operacionCuenta))
                .collect(Collectors.toList());

        historialCuentaDTO.setOperacionCuentaDTOS(operacionCuentaDTOS);
        historialCuentaDTO.setCuentaId(cuentaId);
        historialCuentaDTO.setBalance(cuentaBancaria.getBalance());
        historialCuentaDTO.setCurrentPage(page);
        historialCuentaDTO.setPageSize(size);
        historialCuentaDTO.setTotalPages(operacionesCuenta.getTotalPages());

        return historialCuentaDTO;

    }


}
