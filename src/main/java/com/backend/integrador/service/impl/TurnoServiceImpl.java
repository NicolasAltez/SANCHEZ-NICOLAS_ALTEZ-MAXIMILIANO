package com.backend.integrador.service.impl;

import com.backend.integrador.dto.domicilio.DomicilioSalidaDTO;
import com.backend.integrador.dto.odontologo.OdontologoSalidaDTO;
import com.backend.integrador.dto.paciente.PacienteSalidaDTO;
import com.backend.integrador.dto.turno.TurnoEntradaDTO;
import com.backend.integrador.dto.turno.TurnoSalidaDTO;
import com.backend.integrador.exception.OdontologoNoEncontradoException;
import com.backend.integrador.exception.PacienteNoEncontradoException;
import com.backend.integrador.service.IOdontologoService;
import com.backend.integrador.service.IPacienteService;
import com.backend.integrador.service.ITurnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TurnoServiceImpl implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoServiceImpl.class);
    private final IPacienteService pacienteService;
    private final IOdontologoService odontologoService;

    public TurnoServiceImpl(IPacienteService pacienteService, IOdontologoService odontologoService) {
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoSalidaDTO registrarTurno(TurnoEntradaDTO turnoEntradaDTO) throws OdontologoNoEncontradoException, PacienteNoEncontradoException {
        PacienteSalidaDTO pacienteSalidaDTO = pacienteService.buscarPacientePorId(turnoEntradaDTO.getPacienteId());
        OdontologoSalidaDTO odontologoSalidaDTO =  odontologoService.buscarOdontologoPorId(turnoEntradaDTO.getOdontologoId());

        if (pacienteSalidaDTO == null) {
            throw new PacienteNoEncontradoException("No se encontró el paciente con id: " + turnoEntradaDTO.getPacienteId());
        }

        if (odontologoSalidaDTO == null) {
            throw new OdontologoNoEncontradoException("No se encontró el odontologo con id: " + turnoEntradaDTO.getOdontologoId());
        }

        LOGGER.info("Registrando turno para el paciente: {} y el odontologo: {}", pacienteSalidaDTO, odontologoSalidaDTO);
        return new TurnoSalidaDTO(1, odontologoSalidaDTO, pacienteSalidaDTO, LocalDateTime.now());
    }

    @Override
    public List<TurnoSalidaDTO> listarTurnos() {
        DomicilioSalidaDTO domicilioSalidaDTO = new DomicilioSalidaDTO(1, "calle", 123, "localidad", "provincia");
        PacienteSalidaDTO pacienteSalidaDTO = new PacienteSalidaDTO(1, "maximiliano", "altez", 12312, LocalDate.now(), domicilioSalidaDTO);
        OdontologoSalidaDTO odontologoSalidaDTO = new OdontologoSalidaDTO(1, "matricula", "nicolas", "sanchez");
        TurnoSalidaDTO turnoSalidaDTO = new TurnoSalidaDTO(1, odontologoSalidaDTO, pacienteSalidaDTO, LocalDateTime.now());
        return List.of(turnoSalidaDTO);
    }

    @Override
    public TurnoSalidaDTO buscarTurnoPorId(int id) {
        return null; //TODO implementar
    }

    @Override
    public void eliminarTurno(int id) {
        //TODO implementar
    }

    @Override
    public TurnoSalidaDTO actualizarTurno(TurnoEntradaDTO turnoEntradaDTO) {
        return null; //TODO implementar
    }

}
