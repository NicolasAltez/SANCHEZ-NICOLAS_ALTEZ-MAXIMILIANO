package com.backend.integrador.dto.turno;

import com.backend.integrador.dto.odontologo.OdontologoSalidaDTO;
import com.backend.integrador.dto.paciente.PacienteSalidaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurnoSalidaDTO {
    private Long id;
    private OdontologoSalidaDTO odontologo;
    private PacienteSalidaDTO paciente;
    private LocalDateTime fechaYHora;
}
