package com.backend.integrador.controller;

import com.backend.integrador.dto.domicilio.DomicilioSalidaDTO;
import com.backend.integrador.dto.odontologo.OdontologoSalidaDTO;
import com.backend.integrador.dto.paciente.PacienteSalidaDTO;
import com.backend.integrador.dto.turno.TurnoEntradaDTO;
import com.backend.integrador.dto.turno.TurnoSalidaDTO;
import com.backend.integrador.exception.BadRequestException;
import com.backend.integrador.service.IOdontologoService;
import com.backend.integrador.service.IPacienteService;
import com.backend.integrador.service.ITurnoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.xml.transform.Result;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TurnoControllerTest {

    @MockBean
    private ITurnoService turnoService;

    @MockBean
    private IPacienteService pacienteService;

    @MockBean
    private IOdontologoService odontologoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TurnoEntradaDTO turnoEntradaDTO;

    @BeforeEach
    void setUp() {
        turnoEntradaDTO = TurnoEntradaDTO.builder()
                .pacienteId(1L)
                .odontologoId(1L)
                .fechaYHora(LocalDateTime.now())
                .build();
    }

    @Nested
    class registrarTurnoTests {

        @Test
        void dadoTurnoValido_CuandoRegistrarTurno_EntoncesRetornarCreatedYTurno() throws Exception {

            TurnoEntradaDTO turnoACrear = TurnoEntradaDTO.builder()
                    .pacienteId(1L)
                    .odontologoId(1L)
                    .fechaYHora(LocalDateTime.now().plusDays(1))
                    .build();

            TurnoSalidaDTO turnoCreado = TurnoSalidaDTO.builder()
                    .id(1L)
                    .odontologo(OdontologoSalidaDTO.builder()
                            .id(1L)
                            .nombre("nicolas")
                            .apellido("altez")
                            .numeroDeMatricula("1234ABC")
                            .build())
                    .paciente(PacienteSalidaDTO.builder()
                            .id(1L)
                            .nombre("nicolas")
                            .apellido("sanchez")
                            .dni(12345)
                            .fechaIngreso(LocalDate.of(2045, 12, 20))
                            .domicilioSalidaDTO(DomicilioSalidaDTO.builder()
                                    .id(1L)
                                    .calle("callecita")
                                    .provincia("provincita")
                                    .localidad("localidadddd")
                                    .numero(12345)
                                    .build())
                            .build())
                    .build();

            when(turnoService.registrarTurno(any(TurnoEntradaDTO.class))).thenReturn(turnoCreado);

            ResultActions respuesta = mockMvc.perform(post("/turnos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(turnoACrear)));

            respuesta.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.odontologo.id").value(turnoCreado.getOdontologo().getId()))
                    .andExpect(jsonPath("$.odontologo.nombre").value(turnoCreado.getOdontologo().getNombre()))
                    .andExpect(jsonPath("$.odontologo.apellido").value(turnoCreado.getOdontologo().getApellido()))
                    .andExpect(jsonPath("$.odontologo.numeroDeMatricula").value(turnoCreado.getOdontologo().getNumeroDeMatricula()))
                    .andExpect(jsonPath("$.paciente.id").value(turnoCreado.getPaciente().getId()))
                    .andExpect(jsonPath("$.paciente.nombre").value(turnoCreado.getPaciente().getNombre()))
                    .andExpect(jsonPath("$.paciente.apellido").value(turnoCreado.getPaciente().getApellido()))
                    .andExpect(jsonPath("$.paciente.dni").value(turnoCreado.getPaciente().getDni()))
                    .andExpect(jsonPath("$.paciente.fechaIngreso").value(turnoCreado.getPaciente().getFechaIngreso().toString()))
                    .andExpect(jsonPath("$.paciente.domicilioSalidaDTO.id").value(turnoCreado.getPaciente().getDomicilioSalidaDTO().getId()))
                    .andExpect(jsonPath("$.paciente.domicilioSalidaDTO.calle").value(turnoCreado.getPaciente().getDomicilioSalidaDTO().getCalle()))
                    .andExpect(jsonPath("$.paciente.domicilioSalidaDTO.provincia").value(turnoCreado.getPaciente().getDomicilioSalidaDTO().getProvincia()))
                    .andExpect(jsonPath("$.paciente.domicilioSalidaDTO.localidad").value(turnoCreado.getPaciente().getDomicilioSalidaDTO().getLocalidad()));

            verify(turnoService, times(1)).registrarTurno(any(TurnoEntradaDTO.class));
        }

        @Test
        void dadoTurnoDatosNulos_CuandoRegistrarTurno_EntoncesRetornarBadRequest() throws Exception {
            TurnoEntradaDTO turnoACrear = TurnoEntradaDTO.builder()
                    .pacienteId(null)
                    .odontologoId(null)
                    .fechaYHora(null)
                    .build();

            ResultActions respuesta = mockMvc.perform(post("/turnos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(turnoACrear)));

            respuesta.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.message").value("Validación fallida"))
                    .andExpect(jsonPath("$.errores[*]").isArray())
                    .andExpect(jsonPath("$.errores", hasItems(
                            "El id del odontologo no puede ser nulo",
                            "El id del paciente no puede ser nulo",
                            "La fecha y hora del turno no puede ser nula"
                    )))
                    .andExpect(jsonPath("$.status").value("BAD_REQUEST"));

            verify(turnoService, never()).registrarTurno(any(TurnoEntradaDTO.class));
        }

        @Test
        void dadaFechaYHoraMenorALaHoraActual_CuandoRegistrarTurno_EntoncesRetornarBadRequest() throws Exception {
            TurnoEntradaDTO turnoACrear = TurnoEntradaDTO.builder()
                    .pacienteId(1L)
                    .odontologoId(1L)
                    .fechaYHora(LocalDateTime.now().minusDays(1))
                    .build();

            ResultActions respuesta = mockMvc.perform(post("/turnos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(turnoACrear)));

            respuesta.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.message").value("Validación fallida"))
                    .andExpect(jsonPath("$.errores[*]").isArray())
                    .andExpect(jsonPath("$.errores", hasItems(
                            "La fecha y hora del turno debe ser posterior o igual a la fecha actual"
                    )))
                    .andExpect(jsonPath("$.status").value("BAD_REQUEST"));

            verify(turnoService, never()).registrarTurno(any(TurnoEntradaDTO.class));
        }

        @Test
        void dadoPacienteInvalido_CuandoRegistrarTurno_EntoncesRetornarBadRequest() throws Exception {

            TurnoEntradaDTO turnoACrear = TurnoEntradaDTO.builder()
                    .pacienteId(400L)
                    .odontologoId(1L)
                    .fechaYHora(LocalDateTime.now().plusDays(1))
                    .build();

            when(pacienteService.buscarPacientePorId(400L)).thenReturn(null);
            when(odontologoService.buscarOdontologoPorId(1L)).thenReturn(OdontologoSalidaDTO.builder().build());
            when(turnoService.registrarTurno(turnoACrear)).thenThrow(BadRequestException.class);

            ResultActions respuesta = mockMvc.perform(post("/turnos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(turnoACrear)));

            respuesta.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.message").value("El paciente no existe"))
                    .andExpect(jsonPath("$.status").value("BAD_REQUEST"));

            verify(turnoService, times(1)).registrarTurno(turnoACrear);
            verify(pacienteService, times(1)).buscarPacientePorId(400L);
            verify(odontologoService, times(1)).buscarOdontologoPorId(1L);
        }

        @Test
        void dadoOdontologoInvalido_cuandoRegistrarTurno_EntoncesRetornarBadRequest() throws Exception {

            TurnoEntradaDTO turnoACrear = TurnoEntradaDTO.builder()
                    .pacienteId(1L)
                    .odontologoId(1L)
                    .fechaYHora(LocalDateTime.now().plusDays(1))
                    .build();

            when(pacienteService.buscarPacientePorId(1L)).thenReturn(PacienteSalidaDTO.builder().build());
            when(odontologoService.buscarOdontologoPorId(1L)).thenReturn(null);
            when(turnoService.registrarTurno(turnoACrear)).thenThrow(BadRequestException.class);

            ResultActions respuesta = mockMvc.perform(post("/turnos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(turnoACrear)));

            respuesta.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.message").value("El odontologo no existe"))
                    .andExpect(jsonPath("$.status").value("BAD_REQUEST"));

            verify(turnoService, times(1)).registrarTurno(turnoACrear);
            verify(pacienteService, times(1)).buscarPacientePorId(1L);
            verify(odontologoService, times(1)).buscarOdontologoPorId(1L);
        }
    }

    @Nested
    class listarTurnosTests{
        @Test
        void dadoListaTurnosConElementos_cuandoListarTurnos_EntoncesRetornaOkYListaTurnos() throws Exception {
            List<TurnoSalidaDTO> turnosSalida = List.of(TurnoSalidaDTO.builder()
                    .id(1L)
                    .odontologo(OdontologoSalidaDTO.builder()
                            .id(1L)
                            .nombre("nicolas")
                            .apellido("altez")
                            .numeroDeMatricula("1234ABC")
                            .build())
                    .paciente(PacienteSalidaDTO.builder()
                            .id(1L)
                            .nombre("nicolas")
                            .apellido("sanchez")
                            .dni(12345)
                            .fechaIngreso(LocalDate.of(2045, 12, 20))
                            .domicilioSalidaDTO(DomicilioSalidaDTO.builder()
                                    .id(1L)
                                    .calle("callecita")
                                    .provincia("provincita")
                                    .localidad("localidadddd")
                                    .numero(12345)
                                    .build())
                            .build())
                    .build());

            when(turnoService.listarTurnos()).thenReturn(turnosSalida);

            ResultActions respuesta = mockMvc.perform(get("/turnos"));

            respuesta.andExpect(status().isOk())
                    .andExpect(jsonPath("$",hasSize(1)))
                    .andExpect(jsonPath("$[0].id").value(1L))
                    .andExpect(jsonPath("$[0].odontologo.id").value(turnosSalida.get(0).getOdontologo().getId()))
                    .andExpect(jsonPath("$[0].odontologo.nombre").value(turnosSalida.get(0).getOdontologo().getNombre()))
                    .andExpect(jsonPath("$[0].odontologo.apellido").value(turnosSalida.get(0).getOdontologo().getApellido()))
                    .andExpect(jsonPath("$[0].odontologo.numeroDeMatricula").value(turnosSalida.get(0).getOdontologo().getNumeroDeMatricula()))
                    .andExpect(jsonPath("$[0].paciente.id").value(turnosSalida.get(0).getPaciente().getId()))
                    .andExpect(jsonPath("$[0].paciente.nombre").value(turnosSalida.get(0).getPaciente().getNombre()))
                    .andExpect(jsonPath("$[0].paciente.apellido").value(turnosSalida.get(0).getPaciente().getApellido()))
                    .andExpect(jsonPath("$[0].paciente.dni").value(turnosSalida.get(0).getPaciente().getDni()))
                    .andExpect(jsonPath("$[0].paciente.fechaIngreso").value(turnosSalida.get(0).getPaciente().getFechaIngreso().toString()))
                    .andExpect(jsonPath("$[0].paciente.domicilioSalidaDTO.id").value(turnosSalida.get(0).getPaciente().getDomicilioSalidaDTO().getId()))
                    .andExpect(jsonPath("$[0].paciente.domicilioSalidaDTO.calle").value(turnosSalida.get(0).getPaciente().getDomicilioSalidaDTO().getCalle()))
                    .andExpect(jsonPath("$[0].paciente.domicilioSalidaDTO.provincia").value(turnosSalida.get(0).getPaciente().getDomicilioSalidaDTO().getProvincia()))
                    .andExpect(jsonPath("$[0].paciente.domicilioSalidaDTO.localidad").value(turnosSalida.get(0).getPaciente().getDomicilioSalidaDTO().getLocalidad()));

            verify(turnoService, times(1)).listarTurnos();
        }

        @Test
        void dadoListaTurnosVacia_cuandoListarTurnos_EntoncesRetornaOkYListaVacia() throws Exception {
            when(turnoService.listarTurnos()).thenReturn(List.of());

            ResultActions respuesta = mockMvc.perform(get("/turnos"));

            respuesta.andExpect(status().isOk())
                    .andExpect(content().string("[]"))
                    .andExpect(jsonPath("$",hasSize(0)));

            verify(turnoService, times(1)).listarTurnos();
        }
    }

    @Nested
    class buscarTurnoTests {

        @Test
        void dadoIdTurnoExistente_cuandoBuscarTurno_EntoncesRetornaOkYTurno() throws Exception {
            TurnoSalidaDTO turnoSalida = TurnoSalidaDTO.builder()
                    .id(1L)
                    .odontologo(OdontologoSalidaDTO.builder()
                            .id(1L)
                            .nombre("nicolas")
                            .apellido("altez")
                            .numeroDeMatricula("1234ABC")
                            .build())
                    .paciente(PacienteSalidaDTO.builder()
                            .id(1L)
                            .nombre("nicolas")
                            .apellido("sanchez")
                            .dni(12345)
                            .fechaIngreso(LocalDate.of(2045, 12, 20))
                            .domicilioSalidaDTO(DomicilioSalidaDTO.builder()
                                    .id(1L)
                                    .calle("callecita")
                                    .provincia("provincita")
                                    .localidad("localidadddd")
                                    .numero(12345)
                                    .build())
                            .build())
                    .build();

            when(turnoService.buscarTurnoPorId(1L)).thenReturn(turnoSalida);

            ResultActions respuesta = mockMvc.perform(get("/turnos/1"));

            respuesta.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.odontologo.id").value(turnoSalida.getOdontologo().getId()))
                    .andExpect(jsonPath("$.odontologo.nombre").value(turnoSalida.getOdontologo().getNombre()))
                    .andExpect(jsonPath("$.odontologo.apellido").value(turnoSalida.getOdontologo().getApellido()))
                    .andExpect(jsonPath("$.odontologo.numeroDeMatricula").value(turnoSalida.getOdontologo().getNumeroDeMatricula()))
                    .andExpect(jsonPath("$.paciente.id").value(turnoSalida.getPaciente().getId()))
                    .andExpect(jsonPath("$.paciente.nombre").value(turnoSalida.getPaciente().getNombre()))
                    .andExpect(jsonPath("$.paciente.apellido").value(turnoSalida.getPaciente().getApellido()))
                    .andExpect(jsonPath("$.paciente.dni").value(turnoSalida.getPaciente().getDni()))
                    .andExpect(jsonPath("$.paciente.fechaIngreso").value(turnoSalida.getPaciente().getFechaIngreso().toString()))
                    .andExpect(jsonPath("$.paciente.domicilioSalidaDTO.id").value(turnoSalida.getPaciente().getDomicilioSalidaDTO().getId()))
                    .andExpect(jsonPath("$.paciente.domicilioSalidaDTO.calle").value(turnoSalida.getPaciente().getDomicilioSalidaDTO().getCalle()))
                    .andExpect(jsonPath("$.paciente.domicilioSalidaDTO.provincia").value(turnoSalida.getPaciente().getDomicilioSalidaDTO().getProvincia()))
                    .andExpect(jsonPath("$.paciente.domicilioSalidaDTO.localidad").value(turnoSalida.getPaciente().getDomicilioSalidaDTO().getLocalidad()));

            verify(turnoService, times(1)).buscarTurnoPorId(1L);

        }

        @Test
        void dadoIdTurnoInexistente_cuandoBuscarTurno_EntoncesRetornaOkYNull() throws Exception {
            when(turnoService.buscarTurnoPorId(1L)).thenReturn(null);

            ResultActions respuesta = mockMvc.perform(get("/turnos/1"));

            respuesta.andExpect(jsonPath("$").doesNotExist())
                    .andExpect(status().isOk());

            verify(turnoService, times(1)).buscarTurnoPorId(1L);
        }
    }

    @Nested
    class actualizarTurnoTests{

        @Test
        void dadoTurnoValido_CuandoActualizarTurno_EntoncesRetornarOkYTurnoActualizado() throws Exception {
            TurnoEntradaDTO turnoActualizado = TurnoEntradaDTO.builder()
                    .pacienteId(1L)
                    .odontologoId(1L)
                    .fechaYHora(LocalDateTime.now().plusDays(1))
                    .build();

            PacienteSalidaDTO pacienteSalidaDTO = PacienteSalidaDTO.builder()
                    .id(1L)
                    .nombre("nicolas")
                    .apellido("sanchez")
                    .dni(12345)
                    .fechaIngreso(LocalDate.of(2045, 12, 20))
                    .domicilioSalidaDTO(DomicilioSalidaDTO.builder()
                            .id(1L)
                            .calle("callecita")
                            .provincia("provincita")
                            .localidad("localidadddd")
                            .numero(12345)
                            .build())
                    .build();

            OdontologoSalidaDTO odontologoSalidaDTO = OdontologoSalidaDTO.builder()
                    .id(1L)
                    .nombre("nicolas")
                    .apellido("altez")
                    .numeroDeMatricula("1234ABC")
                    .build();

            TurnoSalidaDTO turnoSalida = TurnoSalidaDTO.builder()
                    .id(1L)
                    .odontologo(odontologoSalidaDTO)
                    .paciente(pacienteSalidaDTO)
                    .build();

            when(turnoService.actualizarTurno(turnoActualizado, 1L)).thenReturn(turnoSalida);
            when(pacienteService.buscarPacientePorId(1L)).thenReturn(pacienteSalidaDTO);
            when(odontologoService.buscarOdontologoPorId(1L)).thenReturn(odontologoSalidaDTO);

            ResultActions respuesta = mockMvc.perform(put("/turnos/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(turnoActualizado)));

            respuesta.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.odontologo.id").value(turnoSalida.getOdontologo().getId()))
                    .andExpect(jsonPath("$.odontologo.nombre").value(turnoSalida.getOdontologo().getNombre()))
                    .andExpect(jsonPath("$.odontologo.apellido").value(turnoSalida.getOdontologo().getApellido()))
                    .andExpect(jsonPath("$.odontologo.numeroDeMatricula").value(turnoSalida.getOdontologo().getNumeroDeMatricula()))
                    .andExpect(jsonPath("$.paciente.id").value(turnoSalida.getPaciente().getId()))
                    .andExpect(jsonPath("$.paciente.nombre").value(turnoSalida.getPaciente().getNombre()))
                    .andExpect(jsonPath("$.paciente.apellido").value(turnoSalida.getPaciente().getApellido()))
                    .andExpect(jsonPath("$.paciente.dni").value(turnoSalida.getPaciente().getDni()))
                    .andExpect(jsonPath("$.paciente.fechaIngreso").value(turnoSalida.getPaciente().getFechaIngreso().toString()))
                    .andExpect(jsonPath("$.paciente.domicilioSalidaDTO.id").value(turnoSalida.getPaciente().getDomicilioSalidaDTO().getId()))
                    .andExpect(jsonPath("$.paciente.domicilioSalidaDTO.calle").value(turnoSalida.getPaciente().getDomicilioSalidaDTO().getCalle()))
                    .andExpect(jsonPath("$.paciente.domicilioSalidaDTO.provincia").value(turnoSalida.getPaciente().getDomicilioSalidaDTO().getProvincia()))
                    .andExpect(jsonPath("$.paciente.domicilioSalidaDTO.localidad").value(turnoSalida.getPaciente().getDomicilioSalidaDTO().getLocalidad()));

            verify(turnoService, times(1)).actualizarTurno(turnoActualizado, 1L);
            verify(pacienteService, times(1)).buscarPacientePorId(1L);
            verify(odontologoService, times(1)).buscarOdontologoPorId(1L);
        }
    }

}