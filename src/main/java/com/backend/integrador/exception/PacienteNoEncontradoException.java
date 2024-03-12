package com.backend.integrador.exception;

public class PacienteNoEncontradoException extends Exception{
    public PacienteNoEncontradoException(String message) {
        super(message);
    }

    public PacienteNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
