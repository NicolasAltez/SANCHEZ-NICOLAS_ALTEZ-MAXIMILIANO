package com.backend.integrador.service;

import com.backend.integrador.dto.turno.TurnoEntradaDTO;
import com.backend.integrador.dto.turno.TurnoSalidaDTO;
import com.backend.integrador.exception.OdontologoNoEncontradoException;
import com.backend.integrador.exception.PacienteNoEncontradoException;

import java.util.List;

public interface ITurnoService {

    TurnoSalidaDTO registrarTurno(TurnoEntradaDTO turnoEntradaDTO) throws OdontologoNoEncontradoException, PacienteNoEncontradoException;
    List<TurnoSalidaDTO> listarTurnos();

    TurnoSalidaDTO buscarTurnoPorId(int id);

    void eliminarTurno(int id);

    TurnoSalidaDTO actualizarTurno(TurnoEntradaDTO turnoEntradaDTO);
}