package com.backend.integrador.dto.odontologo;


import jakarta.validation.constraints.*;

public class OdontologoEntradaDTO {

    @NotNull(message = "El numero de matricula del odontologo no puede ser nulo")
    @NotBlank(message = "El numero de matricula del odontologo no puede estar vacío")
    @Size(min = 2, max = 50, message = "El numero de matricula del odontologo debe tener entre 3 y 50 caracteres")
    private String numeroDeMatricula;

    @NotNull(message = "El nombre del odontologo no puede ser nulo")
    @NotBlank(message = "El nombre del odontologo no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre del odontologo debe tener entre 3 y 50 caracteres")
    private String nombre;


    @NotNull(message = "El apellido del paciente no puede ser nulo")
    @NotBlank(message = "El apellido del paciente no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido del paciente debe tener entre 3 y 50 caracteres")
    private String apellido;

    public OdontologoEntradaDTO(){

    }

    public OdontologoEntradaDTO(String numeroDeMatricula, String nombre, String apellido) {
        this.numeroDeMatricula = numeroDeMatricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNumeroDeMatricula() {
        return numeroDeMatricula;
    }

    public void setNumeroDeMatricula(String numeroDeMatricula) {
        this.numeroDeMatricula = numeroDeMatricula;
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
}
