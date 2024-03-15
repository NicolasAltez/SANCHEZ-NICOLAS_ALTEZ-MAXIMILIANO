package com.backend.integrador.dto.paciente;

import com.backend.integrador.dto.domicilio.DomicilioSalidaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PacienteSalidaDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private int dni;
    private LocalDate fechaIngreso;
    private DomicilioSalidaDTO domicilioSalidaDTO;


}
