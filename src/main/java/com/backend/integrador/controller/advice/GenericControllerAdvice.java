package com.backend.integrador.controller.advice;

import com.backend.integrador.dto.error.ErrorRespuestaDTO;
import com.backend.integrador.exception.OdontologoNoEncontradoException;
import com.backend.integrador.exception.PacienteNoEncontradoException;
import com.backend.integrador.utils.DateUtils;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.validation.FieldError;

import static java.time.ZoneOffset.UTC;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GenericControllerAdvice extends ResponseEntityExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(GenericControllerAdvice.class);

    @ExceptionHandler(OdontologoNoEncontradoException.class)
    public ResponseEntity<Object> odontologoNoEncontradoException(OdontologoNoEncontradoException odontologoNoEncontradoException){
        String messageException = odontologoNoEncontradoException.getMessage();

        ErrorRespuestaDTO respuesta = new ErrorRespuestaDTO(
                messageException,
                DateUtils.obtenerFechaHoraActualFormateada(),
                HttpStatus.BAD_REQUEST);

        LOGGER.error("OdontologoNoEncontradoException: {}", messageException);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PacienteNoEncontradoException.class)
    public ResponseEntity<Object> pacienteNoEncontradoException(PacienteNoEncontradoException pacienteNoEncontradoException){
        String messageException = pacienteNoEncontradoException.getMessage();

        ErrorRespuestaDTO respuesta = new ErrorRespuestaDTO(
                messageException,
                DateUtils.obtenerFechaHoraActualFormateada(),
                HttpStatus.BAD_REQUEST);

        LOGGER.error("PacienteNoEncontradoException: {}", messageException);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errores = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        Map<String, Object> responseBody = Map.of(
                "message", "Validación fallida",
                "errores", errores,
                "timestamp", LocalDateTime.now(UTC),
                "status", HttpStatus.BAD_REQUEST

        );
        LOGGER.error("error trying validate request body: ", ex);
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

}
