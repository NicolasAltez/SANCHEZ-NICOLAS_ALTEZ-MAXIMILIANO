package com.backend.integrador.dto.domicilio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DomicilioSalidaDTO {
    private Long id;
    private String calle;
    private int numero;
    private String localidad;
    private String provincia;

}
