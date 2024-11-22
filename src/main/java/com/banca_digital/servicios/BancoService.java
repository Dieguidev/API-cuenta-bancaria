package com.banca_digital.servicios;


import com.banca_digital.entidades.CuentaActual;
import com.banca_digital.entidades.CuentaAhorro;
import com.banca_digital.entidades.CuentaBancaria;
import com.banca_digital.repositorios.CuentaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BancoService {
    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    public void consultar() {
        CuentaBancaria cuentaBancariaBBDD = cuentaBancariaRepository.findById("00bd5a38-e5ac-4892-be6d-5594f27827f4").orElse(null);
        if (cuentaBancariaBBDD != null) {
            System.out.println("**********************************");
            System.out.println("ID : " + cuentaBancariaBBDD.getId());
            System.out.println("Balance de la cuenta : " + cuentaBancariaBBDD.getBalance());
            System.out.println("Estado de la cuenta : " + cuentaBancariaBBDD.getEstadoCuenta());
            System.out.println("Fechade creacion : " + cuentaBancariaBBDD.getFechaCreacion());
            System.out.println("Nombre del cliente : " + cuentaBancariaBBDD.getCliente().getNombre());
            System.out.println("Nonbre de la clase : " + cuentaBancariaBBDD.getClass().getSimpleName());

            if (cuentaBancariaBBDD instanceof CuentaActual) {
                System.out.println("Sobregiro : " + ((CuentaActual) cuentaBancariaBBDD).getSobregiro());
            } else if(cuentaBancariaBBDD instanceof CuentaAhorro){
                System.out.println("Tasa de interes : " + ((CuentaAhorro) cuentaBancariaBBDD).getTasaDeInteres());
            }

            cuentaBancariaBBDD.getOperacionCuenta().forEach(operacionCuenta -> {
                System.out.println("----------------------------------");
                System.out.println("Tipo de operacion : " + operacionCuenta.getTipoOperacion());
                System.out.println("Fecha de la operacion : " + operacionCuenta.getFechaOperacion());
                System.out.println("Monto de la operacion : " + operacionCuenta.getMonto());
                System.out.println("----------------------------------");
            });
            System.out.println("**********************************");
        }
    }
}
