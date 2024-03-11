package com.backend.integrador.dto.paciente;

import com.backend.integrador.dto.domicilio.DomicilioEntradaDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class PacienteEntradaDTO {
    @NotNull(message = "El nombre del paciente no puede ser nulo")
    @NotBlank(message = "El nombre del paciente no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre del paciente debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotNull(message = "El apellido del paciente no puede ser nulo")
    @NotBlank(message = "El apellido del paciente no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido del paciente debe tener entre 3 y 50 caracteres")
    private String apellido;
    @Positive(message = "El dni del paciente no puede ser nulo ni negativo")
    private int dni;
    @FutureOrPresent(message = "La fecha de ingreso debe ser posterior o igual a la fecha actual")
    @NotNull(message = "La fecha de ingreso no puede ser nula")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaIngreso;

    @NotNull(message = "El domicilio del paciente no puede ser nulo")
    @Valid
    private DomicilioEntradaDTO domicilioEntradaDTO;

    public PacienteEntradaDTO(){

    }

    public PacienteEntradaDTO(String nombre, String apellido, int dni, LocalDate fechaIngreso, DomicilioEntradaDTO domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilioEntradaDTO = domicilio;
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

    public DomicilioEntradaDTO getDomicilioEntradaDTO() {
        return domicilioEntradaDTO;
    }

    public void setDomicilioEntradaDTO(DomicilioEntradaDTO domicilioEntradaDTO) {
        this.domicilioEntradaDTO = domicilioEntradaDTO;
    }
}
