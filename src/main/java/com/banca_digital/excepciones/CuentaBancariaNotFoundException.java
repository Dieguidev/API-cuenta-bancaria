package com.banca_digital.excepciones;

public class CuentaBancariaNotFoundException extends Exception {
    public CuentaBancariaNotFoundException(String message) {
        super(message);
    }
}
