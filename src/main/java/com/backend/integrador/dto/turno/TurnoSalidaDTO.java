package com.backend.integrador.dto.turno;

import com.backend.integrador.dto.odontologo.OdontologoSalidaDTO;
import com.backend.integrador.dto.paciente.PacienteSalidaDTO;


import java.time.LocalDateTime;

public class TurnoSalidaDTO {
    private int id;
    private OdontologoSalidaDTO odontologo;
    private PacienteSalidaDTO paciente;
    private LocalDateTime fechaYHora;

    public TurnoSalidaDTO() {
    }

    public TurnoSalidaDTO(int id, OdontologoSalidaDTO odontologo, PacienteSalidaDTO paciente, LocalDateTime fechaYHora) {
        this.id = id;
        this.odontologo = odontologo;
        this.paciente = paciente;
        this.fechaYHora = fechaYHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OdontologoSalidaDTO getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoSalidaDTO odontologo) {
        this.odontologo = odontologo;
    }

    public PacienteSalidaDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteSalidaDTO paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }
}
