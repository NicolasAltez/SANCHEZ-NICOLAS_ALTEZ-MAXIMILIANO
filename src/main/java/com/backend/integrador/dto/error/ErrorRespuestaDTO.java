package com.backend.integrador.dto.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorRespuestaDTO {
    private String mensaje;
    private String timestamp;
    private HttpStatus status;
}
