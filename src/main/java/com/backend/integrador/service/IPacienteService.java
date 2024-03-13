package com.backend.integrador.service;

import com.backend.integrador.dto.paciente.PacienteEntradaDTO;
import com.backend.integrador.dto.paciente.PacienteSalidaDTO;

import java.util.List;

public interface IPacienteService {

    PacienteSalidaDTO guardarPaciente(PacienteEntradaDTO paciente);

    List<PacienteSalidaDTO> listarPacientes();

    PacienteSalidaDTO buscarPacientePorId(Long id);

    PacienteSalidaDTO actualizarPaciente(PacienteEntradaDTO paciente);

    void eliminarPaciente(Long id);
}
