package com.backend.integrador.controller;

import com.backend.integrador.dto.odontologo.OdontologoEntradaDTO;
import com.backend.integrador.dto.odontologo.OdontologoSalidaDTO;
import com.backend.integrador.service.IOdontologoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoSalidaDTO> buscarOdontologoPorId(@PathVariable int id) {
        return new ResponseEntity<>(odontologoService.buscarOdontologoPorId(id), HttpStatus.OK);
    }

    @GetMapping
    public HttpEntity<List<OdontologoSalidaDTO>> buscarTodosLosOdontologos() {
        return new ResponseEntity<>(odontologoService.buscarTodosLosOdontologos(), HttpStatus.OK);
    }

    @PostMapping
    public HttpEntity<OdontologoSalidaDTO> guardarOdontologo(@RequestBody @Valid OdontologoEntradaDTO odontologo) {
        return new ResponseEntity<>(odontologoService.guardarOdontologo(odontologo), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public HttpEntity<String> eliminarOdontologo(@PathVariable int id){
        odontologoService.eliminarOdontologo(id);
        return new ResponseEntity<>("Se borro correctamente el odontologo", HttpStatus.NO_CONTENT);
    }


    @PutMapping
    public ResponseEntity<OdontologoSalidaDTO> actualizarOdontologo(@RequestBody @Valid OdontologoEntradaDTO odontologo) {
        return new ResponseEntity<>(odontologoService.actualizarOdontologo(odontologo), HttpStatus.OK);
    }

}
