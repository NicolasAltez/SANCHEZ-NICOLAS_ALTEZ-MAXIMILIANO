package com.backend.integrador.dto.odontologo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OdontologoSalidaDTO {

    private Long id;
    private String numeroDeMatricula;
    private String nombre;
    private String apellido;


}
