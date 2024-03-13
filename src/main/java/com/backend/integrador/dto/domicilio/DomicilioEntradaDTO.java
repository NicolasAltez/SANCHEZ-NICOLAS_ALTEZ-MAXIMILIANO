package com.backend.integrador.dto.domicilio;

import jakarta.validation.constraints.*;

public class DomicilioEntradaDTO {
    @NotNull(message = "El campo calle no puede ser nulo")
    @NotBlank(message = "El campo calle no puede estar en blanco")
    @Size(min = 3, max = 255, message = "La calle debe tener entre 3 y 255 caracteres")
    private String calle;

    @Positive(message = "El numero no puede ser nulo o menor a cero")
    @Digits(integer = 8, fraction = 0, message = "El número debe tener como máximo 8 dígitos")
    private int numero;

    @NotNull(message = "El campo localidad no puede ser nulo")
    @NotBlank(message = "El campo localidad no puede estar en blanco")
    @Size(min = 2, max = 255, message = "La localidad debe tener entre 2 y 255 caracteres")
    private String localidad;

    @NotNull(message = "El campo provincia no puede ser nulo")
    @NotBlank(message = "El campo provincia no puede estar en blanco")
    @Size(min = 2, max = 255, message = "La provincia debe tener entre 2 y 255 caracteres")
    private String provincia;

    public DomicilioEntradaDTO() {
    }

    public DomicilioEntradaDTO(String calle, int numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
