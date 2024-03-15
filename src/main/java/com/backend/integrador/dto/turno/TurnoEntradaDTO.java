package com.backend.integrador.dto.turno;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurnoEntradaDTO {

    @NotNull(message = "El id del odontologo no puede ser nulo")
    @Positive(message = "El id del odontologo debe ser un número positivo")
    private Long odontologoId;

    @NotNull(message = "El id del paciente no puede ser nulo")
    @Positive(message = "El id del paciente debe ser un número positivo")
    private Long pacienteId;

    @FutureOrPresent(message = "La fecha y hora del turno debe ser posterior o igual a la fecha actual")
    @NotNull(message = "La fecha y hora del turno no puede ser nula")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaYHora;
}
