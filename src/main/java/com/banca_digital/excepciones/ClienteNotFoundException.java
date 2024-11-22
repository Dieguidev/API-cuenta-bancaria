package com.banca_digital.excepciones;

public class ClienteNotFoundException extends Exception{
    public ClienteNotFoundException(String message) {
        super(message);
    }
}
