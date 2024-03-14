package com.backend.integrador.dto.paciente;

import com.backend.integrador.dto.domicilio.DomicilioSalidaDTO;

import java.time.LocalDate;

public class PacienteSalidaDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private int dni;
    private LocalDate fechaIngreso;
    private DomicilioSalidaDTO domicilioSalidaDTO;

    public PacienteSalidaDTO() {
    }

    public PacienteSalidaDTO(Long id, String nombre, String apellido, int dni, LocalDate fechaIngreso, DomicilioSalidaDTO domicilioSalidaDTO) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilioSalidaDTO = domicilioSalidaDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public DomicilioSalidaDTO getDomicilioSalidaDTO() {
        return domicilioSalidaDTO;
    }

    public void setDomicilioSalidaDTO(DomicilioSalidaDTO domicilioSalidaDTO) {
        this.domicilioSalidaDTO = domicilioSalidaDTO;
    }
}
