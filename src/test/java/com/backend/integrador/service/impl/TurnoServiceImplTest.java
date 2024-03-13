package com.backend.integrador.service.impl;
import com.backend.integrador.dto.turno.TurnoEntradaDTO;
import com.backend.integrador.entity.Odontologo;
import com.backend.integrador.entity.Paciente;
import com.backend.integrador.entity.Turno;
import com.backend.integrador.exception.OdontologoNoEncontradoException;
import com.backend.integrador.exception.PacienteNoEncontradoException;
import com.backend.integrador.repository.OdontologoRepository;
import com.backend.integrador.repository.PacienteRepository;
import com.backend.integrador.repository.TurnoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TurnoServiceImplTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private OdontologoRepository odontologoRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private TurnoServiceImpl turnoService;

    private TurnoEntradaDTO turnoEntradaDTO;
    private Paciente paciente;
    private Odontologo odontologo;
    private Turno turno;

    @BeforeEach
    void setUp() {
        turnoEntradaDTO = new TurnoEntradaDTO();
        turnoEntradaDTO.setPacienteId(1);
        turnoEntradaDTO.setOdontologoId(1);

        paciente = new Paciente();
        odontologo = new Odontologo();
        turno = new Turno();

        when(pacienteRepository.findById(turnoEntradaDTO.getPacienteId())).thenReturn(Optional.of(paciente));
        when(odontologoRepository.findById(turnoEntradaDTO.getOdontologoId())).thenReturn(Optional.of(odontologo));
        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);
        when(modelMapper.map(any(Turno.class), eq(Turno.class))).thenReturn(turno);
    }

    @Test
    void shouldRegisterTurno() throws PacienteNoEncontradoException, OdontologoNoEncontradoException {
        turnoService.registrarTurno(turnoEntradaDTO);

        verify(pacienteRepository).findById(turnoEntradaDTO.getPacienteId());
        verify(odontologoRepository).findById(turnoEntradaDTO.getOdontologoId());
        verify(turnoRepository).save(any(Turno.class));
    }

    @Test
    void shouldThrowPacienteNoEncontradoException() {
        when(pacienteRepository.findById(turnoEntradaDTO.getPacienteId())).thenReturn(Optional.empty());

        assertThrows(PacienteNoEncontradoException.class, () -> turnoService.registrarTurno(turnoEntradaDTO));
    }

    @Test
    void shouldThrowOdontologoNoEncontradoException() {
        when(odontologoRepository.findById(turnoEntradaDTO.getOdontologoId())).thenReturn(Optional.empty());

        assertThrows(OdontologoNoEncontradoException.class, () -> turnoService.registrarTurno(turnoEntradaDTO));
    }
}