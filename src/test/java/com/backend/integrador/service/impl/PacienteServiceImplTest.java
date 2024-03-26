package com.backend.integrador.service.impl;

import com.backend.integrador.dto.domicilio.DomicilioEntradaDTO;
import com.backend.integrador.dto.paciente.PacienteEntradaDTO;
import com.backend.integrador.dto.paciente.PacienteSalidaDTO;
import com.backend.integrador.entity.Domicilio;
import com.backend.integrador.entity.Paciente;
import com.backend.integrador.exception.ResourceNotFoundException;
import com.backend.integrador.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PacienteServiceImplTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private PacienteEntradaDTO pacienteEntradaDTO;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        pacienteEntradaDTO = crearPacienteEntradaDTO("nicolas", "sanchez", 12345);
        paciente = crearPaciente(1L, "nicolas", "sanchez", 12345);
    }


    @Test
    void deberiaGuardarUnPaciente_YRetornarUnPacienteConSuId() {
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);
        PacienteSalidaDTO pacienteGuardado = pacienteService.guardarPaciente(pacienteEntradaDTO);

        verify(pacienteRepository, times(1)).save(any(Paciente.class));

        assertNotNull(pacienteGuardado.getId());
    }


    @Test
    void deberiaBuscarUnPacientePorId_YRetornarElPacienteEncontrado() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        PacienteSalidaDTO pacienteBuscado = pacienteService.buscarPacientePorId(1L);

        verify(pacienteRepository, times(1)).findById(1L);

        assertNotNull(pacienteBuscado);
        assertEquals(paciente.getId(), pacienteBuscado.getId());
    }

    @Test
    void deberiaBuscarUnPacientePorId_YRetornarNull() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.empty());
        PacienteSalidaDTO pacienteBuscado = pacienteService.buscarPacientePorId(1L);

        verify(pacienteRepository, times(1)).findById(1L);

        assertNull(pacienteBuscado);
    }

    @Test
    void deberiaBuscarUnaListaDePacientes_YRetornarUnaListaConElementos() {
        when(pacienteRepository.findAll()).thenReturn(List.of(paciente));
        List<PacienteSalidaDTO> pacientes = pacienteService.listarPacientes();

        verify(pacienteRepository, times(1)).findAll();

        assertFalse(pacientes.isEmpty());
        assertEquals(1, pacientes.size());
        assertEquals(paciente.getId(), pacientes.get(0).getId());
    }

    @Test
    void deberiaBuscarUnaListaDePacientes_YRetornarUnaListaVacia() {
        when(pacienteRepository.findAll()).thenReturn(List.of());
        List<PacienteSalidaDTO> pacientes = pacienteService.listarPacientes();

        verify(pacienteRepository, times(1)).findAll();

        assertTrue(pacientes.isEmpty());
    }

    @Test
    void deberiaIntentarEliminarUnPacienteInexistente_YLanzarExcepcionConMensaje() {
        when(pacienteRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
            pacienteService.eliminarPaciente(1L);
        });

        verify(pacienteRepository, times(1)).existsById(1L);
        verify(pacienteRepository, never()).deleteById(anyLong());

        assertEquals("No se encontró el paciente con id: 1", resourceNotFoundException.getMessage());
    }

    @Test
    void deberiaEliminarUnPaciente_YNoRetornarNada() throws ResourceNotFoundException {
        when(pacienteRepository.existsById(1L)).thenReturn(true);
        pacienteService.eliminarPaciente(1L);

        verify(pacienteRepository, times(1)).existsById(1L);
        verify(pacienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void deberiaActualizarUnPacienteExistente_YRetornarElPacienteActualizado(){
        PacienteEntradaDTO pacienteEntradaActualizar = crearPacienteEntradaDTO("nuevoNombre","nuevoApellido",3333);
        Paciente crearPacienteActualizado = crearPaciente(1L,"nuevoNombre","nuevoApellido",3333);

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(crearPacienteActualizado);

        PacienteSalidaDTO pacienteActualizado = pacienteService.actualizarPaciente(pacienteEntradaActualizar,1L);

        verify(pacienteRepository, times(1)).save(any(Paciente.class));
        verify(pacienteRepository,times(1)).findById(1L);

        assertNotNull(pacienteActualizado);
        assertEquals(paciente.getId(),pacienteActualizado.getId());
        assertNotEquals(paciente.getNombre(),pacienteActualizado.getNombre());
    }


    //METODOS DE UTILIDAD
    private PacienteEntradaDTO crearPacienteEntradaDTO(String nombre, String apellido, int dni) {
        return PacienteEntradaDTO.builder()
                .nombre(nombre)
                .apellido(apellido)
                .dni(dni)
                .domicilioEntradaDTO(
                        DomicilioEntradaDTO.builder()
                                .calle("calle")
                                .localidad("localidad")
                                .numero(1234)
                                .provincia("provincia")
                                .build()
                )
                .build();
    }

    private Paciente crearPaciente(Long id, String nombre, String apellido, int dni) {
        return Paciente.builder()
                .id(id)
                .nombre(nombre)
                .apellido(apellido)
                .dni(dni)
                .fechaIngreso(LocalDate.of(2027,12,27))
                .domicilio(Domicilio.builder()
                        .id(1L)
                        .calle("Avenida Libertador")
                        .numero(123)
                        .provincia("Canelones")
                        .localidad("Pando city")
                        .build())
                .build();
    }

}