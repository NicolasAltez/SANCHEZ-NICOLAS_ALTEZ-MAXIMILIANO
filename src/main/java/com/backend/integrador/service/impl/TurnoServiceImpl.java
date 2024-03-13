package com.backend.integrador.service.impl;

import com.backend.integrador.dto.domicilio.DomicilioSalidaDTO;
import com.backend.integrador.dto.odontologo.OdontologoSalidaDTO;
import com.backend.integrador.dto.paciente.PacienteSalidaDTO;
import com.backend.integrador.dto.turno.TurnoEntradaDTO;
import com.backend.integrador.dto.turno.TurnoSalidaDTO;
import com.backend.integrador.entity.Odontologo;
import com.backend.integrador.entity.Paciente;
import com.backend.integrador.entity.Turno;
import com.backend.integrador.exception.OdontologoNoEncontradoException;
import com.backend.integrador.exception.PacienteNoEncontradoException;
import com.backend.integrador.repository.OdontologoRepository;
import com.backend.integrador.repository.PacienteRepository;
import com.backend.integrador.repository.TurnoRepository;
import com.backend.integrador.service.IOdontologoService;
import com.backend.integrador.service.IPacienteService;
import com.backend.integrador.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoServiceImpl implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoServiceImpl.class);
    private ModelMapper modelMapper;
    private  TurnoRepository turnoRepository;
    private PacienteRepository pacienteRepository;
    private OdontologoRepository odontologoRepository;

    public TurnoServiceImpl(PacienteRepository pacienteRepository, OdontologoRepository odontologoRepository, TurnoRepository turnoRepository,ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
        this.turnoRepository = turnoRepository;
    }

    @Override
    public TurnoSalidaDTO registrarTurno(TurnoEntradaDTO turnoEntradaDTO) throws OdontologoNoEncontradoException, PacienteNoEncontradoException {
        Paciente paciente = pacienteRepository.findById(turnoEntradaDTO.getPacienteId()).orElseThrow(
                () -> new PacienteNoEncontradoException("No se encontró el paciente con id: " + turnoEntradaDTO.getPacienteId())
        );

        Odontologo odontologo = odontologoRepository.findById(turnoEntradaDTO.getOdontologoId()).orElseThrow(
                () -> new OdontologoNoEncontradoException("No se encontró el odontologo con id: " + turnoEntradaDTO.getOdontologoId())
        );

        LOGGER.info("Registrando turno para el paciente: {} y el odontologo: {}", paciente, odontologo);

        Turno turnoGuardado = turnoRepository.save(modelMapper.map(turnoEntradaDTO, Turno.class));

        return modelMapper.map(turnoGuardado, TurnoSalidaDTO.class);
    }

    @Override
    public List<TurnoSalidaDTO> listarTurnos() {
        return turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDTO.class))
                .toList();
    }

    @Override
    public TurnoSalidaDTO buscarTurnoPorId(int id) {
        return turnoRepository.findById(id).map(turno -> modelMapper.map(turno, TurnoSalidaDTO.class))
                .orElseGet(() -> {
                    LOGGER.info("No se encontró el turno con id: {}", id);
                    return null;
                });
    }

    @Override
    public void eliminarTurno(int id) {
        turnoRepository.findById(id).ifPresentOrElse(
                turnoRepository::delete,
                () -> LOGGER.info("No se encontró el turno a eliminar con id: {}", id)
        );
    }

    @Override
    public TurnoSalidaDTO actualizarTurno(TurnoEntradaDTO turnoEntradaDTO) {
        return null; //TODO implementar
    }

}
