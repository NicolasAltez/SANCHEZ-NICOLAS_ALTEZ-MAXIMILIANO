package com.backend.integrador.exception;

public class OdontologoNoEncontradoException extends Exception{
    public OdontologoNoEncontradoException(String message) {
        super(message);
    }

    public OdontologoNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
