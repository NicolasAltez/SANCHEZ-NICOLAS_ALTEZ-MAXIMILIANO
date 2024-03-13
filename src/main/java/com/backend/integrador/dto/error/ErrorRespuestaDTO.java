package com.backend.integrador.dto.error;


import org.springframework.http.HttpStatus;

public class ErrorRespuestaDTO {
    private String mensaje;
    private String timestamp;
    private HttpStatus status;

    public ErrorRespuestaDTO() {
    }

    public ErrorRespuestaDTO(String mensaje, String timestamp, HttpStatus status) {
        this.mensaje = mensaje;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
