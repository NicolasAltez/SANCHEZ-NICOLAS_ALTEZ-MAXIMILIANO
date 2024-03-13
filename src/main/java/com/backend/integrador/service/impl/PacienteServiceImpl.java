package com.backend.integrador.service.impl;

import com.backend.integrador.dto.paciente.PacienteEntradaDTO;
import com.backend.integrador.dto.paciente.PacienteSalidaDTO;
import com.backend.integrador.entity.Paciente;
import com.backend.integrador.repository.PacienteRepository;
import com.backend.integrador.service.IPacienteService;
import com.backend.integrador.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements IPacienteService {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteServiceImpl.class);

    private PacienteRepository pacienteRepository;

    private ModelMapper modelMapper;

    public PacienteServiceImpl(PacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public PacienteSalidaDTO guardarPaciente(PacienteEntradaDTO paciente) {
        LOGGER.info("Guardando paciente: {}", JsonPrinter.toString(paciente));
        Paciente pacienteGuardado = pacienteRepository.save(modelMapper.map(paciente, Paciente.class));
        LOGGER.info("Paciente guardado: {}", JsonPrinter.toString(pacienteGuardado));
        return modelMapper.map(pacienteGuardado, PacienteSalidaDTO.class);
    }

    @Override
    public List<PacienteSalidaDTO> listarPacientes() {
        return pacienteRepository.findAll()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDTO.class))
                .toList();
    }

    @Override
    public PacienteSalidaDTO buscarPacientePorId(Long id) {
        return pacienteRepository.findById(id).map(paciente -> modelMapper.map(paciente,PacienteSalidaDTO.class
        )).orElseGet(() -> {
            LOGGER.info("No se encontró el paciente con id: {}", id);
            return null;
        });
    }

    @Override
    public PacienteSalidaDTO actualizarPaciente(PacienteEntradaDTO paciente) {
        Optional<Paciente> pacienteAModificar = pacienteRepository.findByDni(paciente.getDni());
        if (pacienteAModificar.isPresent()) {
            Paciente pacienteModificado = pacienteRepository.save(modelMapper.map(paciente, Paciente.class));
            LOGGER.info("Paciente modificado: {}", pacienteModificado);
            return modelMapper.map(pacienteModificado, PacienteSalidaDTO.class);
        } else {
            LOGGER.info("No se encontró el paciente a modificar con  el dni : {}", paciente.getDni());
            return null;
        }
    }

    @Override
    public void eliminarPaciente(Long id) {
        pacienteRepository.findById(id).ifPresentOrElse(
                pacienteRepository::delete,
                () -> LOGGER.info("No se encontró el paciente a eliminar con id: {}", id)
        );
    }

    private void configureMapping() {
        modelMapper.typeMap(PacienteEntradaDTO.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDTO::getDomicilioEntradaDTO, Paciente::setDomicilio));


        modelMapper.typeMap(Paciente.class, PacienteSalidaDTO.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDTO::setDomicilioSalidaDTO));

    }
}
