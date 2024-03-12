package com.backend.integrador.controller.advice;

import com.backend.integrador.exception.OdontologoNoEncontradoException;
import com.backend.integrador.exception.PacienteNoEncontradoException;
import com.backend.integrador.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GenericControllerAdvice {

    private final Logger LOGGER = LoggerFactory.getLogger(GenericControllerAdvice.class);
    private static final String MESSAGE = "message";
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";


    @ExceptionHandler(OdontologoNoEncontradoException.class)
    public ResponseEntity<Object> odontologoNoEncontradoException(OdontologoNoEncontradoException odontologoNoEncontradoException){
        String messageException = odontologoNoEncontradoException.getMessage();

        Map<String,Object> respuesta = Map.of(
                MESSAGE, messageException,
                TIMESTAMP, DateUtils.obtenerFechaHoraActualFormateada(),
                STATUS, HttpStatus.BAD_REQUEST
        );

        LOGGER.error("OdontologoNoEncontradoException: {}", messageException);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PacienteNoEncontradoException.class)
    public ResponseEntity<Object> pacienteNoEncontradoException(PacienteNoEncontradoException pacienteNoEncontradoException){
        String messageException = pacienteNoEncontradoException.getMessage();

        Map<String,Object> respuesta = Map.of(
                MESSAGE, messageException,
                TIMESTAMP, DateUtils.obtenerFechaHoraActualFormateada(),
                STATUS, HttpStatus.BAD_REQUEST
        );

        LOGGER.error("PacienteNoEncontradoException: {}", messageException);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacionErroresDTOs(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> respuesta = Map.of(
                MESSAGE, "Validación fallida",
                TIMESTAMP, DateUtils.obtenerFechaHoraActualFormateada(),
                STATUS, HttpStatus.BAD_REQUEST.value(),
                "errores", errores
        );

        LOGGER.error("MethodArgumentNotValidException: {}", errores);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

}
