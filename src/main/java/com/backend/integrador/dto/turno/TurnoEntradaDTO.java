package com.backend.integrador.dto.turno;



import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class TurnoEntradaDTO {
    private int odontologoId;
    private int pacienteId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaYHora;

    public TurnoEntradaDTO() {
    }

    public TurnoEntradaDTO(int odontologoId, int pacienteId, LocalDateTime fechaYHora) {
        this.odontologoId = odontologoId;
        this.pacienteId = pacienteId;
        this.fechaYHora = fechaYHora;
    }

    public int getOdontologoId() {
        return odontologoId;
    }

    public void setOdontologoId(int odontologoId) {
        this.odontologoId = odontologoId;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }
}
