package com.backend.integrador.controller;

import com.backend.integrador.dto.turno.TurnoEntradaDTO;
import com.backend.integrador.dto.turno.TurnoSalidaDTO;
import com.backend.integrador.exception.OdontologoNoEncontradoException;
import com.backend.integrador.exception.PacienteNoEncontradoException;
import com.backend.integrador.service.ITurnoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<TurnoSalidaDTO> registrarTurno(@RequestBody @Valid TurnoEntradaDTO turno) throws PacienteNoEncontradoException, OdontologoNoEncontradoException {
        return new ResponseEntity<>(turnoService.registrarTurno(turno), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TurnoSalidaDTO>> listarTurnos() {
        return new ResponseEntity<>(turnoService.listarTurnos(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id) {
        turnoService.eliminarTurno(id);
        return new ResponseEntity<>("Se borro correctamente el turno", HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<TurnoSalidaDTO> actualizarTurno(@RequestBody @Valid TurnoEntradaDTO turnoEntradaDTO ){
        return new ResponseEntity<>(turnoService.actualizarTurno(turnoEntradaDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoSalidaDTO> buscarTurno(@PathVariable int id) {
        return new ResponseEntity<>(turnoService.buscarTurnoPorId(id), HttpStatus.OK);
    }

}
