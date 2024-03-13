package com.backend.integrador.dto.turno;



import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.LongAccumulator;

public class TurnoEntradaDTO {
    private Long odontologoId;
    private Long pacienteId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaYHora;

    public TurnoEntradaDTO() {
    }

    public TurnoEntradaDTO(Long odontologoId, Long pacienteId, LocalDateTime fechaYHora) {
        this.odontologoId = odontologoId;
        this.pacienteId = pacienteId;
        this.fechaYHora = fechaYHora;
    }

    public Long getOdontologoId() {
        return odontologoId;
    }

    public void setOdontologoId(Long odontologoId) {
        this.odontologoId = odontologoId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }
}
