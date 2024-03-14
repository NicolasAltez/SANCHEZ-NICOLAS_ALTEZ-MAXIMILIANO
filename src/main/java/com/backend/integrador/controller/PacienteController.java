package com.backend.integrador.controller;

import com.backend.integrador.dto.paciente.PacienteEntradaDTO;
import com.backend.integrador.dto.paciente.PacienteSalidaDTO;
import com.backend.integrador.service.IPacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<PacienteSalidaDTO> buscarPacientePorId(@PathVariable Long id) {
        return new ResponseEntity<>(pacienteService.buscarPacientePorId(id), HttpStatus.OK);
    }

    @GetMapping
    public HttpEntity<List<PacienteSalidaDTO>> listarPacientes() {
        return new ResponseEntity<>(pacienteService.listarPacientes(), HttpStatus.OK);//ver
    }

    @PostMapping
    public HttpEntity<PacienteSalidaDTO> guardarPaciente(@RequestBody @Valid PacienteEntradaDTO paciente) {
        return new ResponseEntity<>(pacienteService.guardarPaciente(paciente), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public HttpEntity<String> eliminarPaciente(@PathVariable Long id){
        pacienteService.eliminarPaciente(id);
        return new ResponseEntity<>("Se borro correctamente el paciente", HttpStatus.NO_CONTENT);
    }


    @PutMapping
    public ResponseEntity<PacienteSalidaDTO> actualizarPaciente(@RequestBody @Valid PacienteEntradaDTO paciente) {
        return new ResponseEntity<>(pacienteService.actualizarPaciente(paciente), HttpStatus.OK);
    }

}