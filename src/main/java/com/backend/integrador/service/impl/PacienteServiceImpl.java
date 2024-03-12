package com.backend.integrador.service.impl;

import com.backend.integrador.dao.IDao;
import com.backend.integrador.dto.paciente.PacienteEntradaDTO;
import com.backend.integrador.dto.paciente.PacienteSalidaDTO;
import com.backend.integrador.entity.Paciente;
import com.backend.integrador.service.IPacienteService;
import com.backend.integrador.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements IPacienteService {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteServiceImpl.class);

    private IDao<Paciente> pacienteIDao;

    private ModelMapper modelMapper;

    public PacienteServiceImpl(IDao<Paciente> pacienteIDao, ModelMapper modelMapper) {
        this.pacienteIDao = pacienteIDao;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public PacienteSalidaDTO guardarPaciente(PacienteEntradaDTO paciente) {
        LOGGER.info("Guardando paciente: {}", JsonPrinter.toString(paciente));
        Paciente pacienteEntidad = pacienteIDao.guardar(modelMapper.map(paciente, Paciente.class));
        LOGGER.info("Paciente guardado: {}", JsonPrinter.toString(pacienteEntidad));
        return modelMapper.map(pacienteEntidad, PacienteSalidaDTO.class);
    }

    @Override
    public List<PacienteSalidaDTO> listarPacientes() {
        return pacienteIDao.buscarTodos()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDTO.class))
                .toList();
    }

    @Override
    public PacienteSalidaDTO buscarPacientePorId(int id) {
        Paciente pacienteBuscado = pacienteIDao.buscarPorId(id);
        if (pacienteBuscado != null) {
            return modelMapper.map(pacienteBuscado, PacienteSalidaDTO.class);
        } else {
            LOGGER.info("No se encontró el paciente con id: {}", id);
            return null;
        }
    }

    @Override
    public PacienteSalidaDTO actualizarPaciente(PacienteEntradaDTO paciente) {
        LOGGER.info("PacienteEntradaDTO: {}", JsonPrinter.toString(paciente));
        Paciente pacienteEntidad = pacienteIDao.actualizar(modelMapper.map(paciente, Paciente.class));
        LOGGER.info("PacienteSalidaDTO: {}", JsonPrinter.toString(pacienteEntidad));
        return modelMapper.map(pacienteEntidad, PacienteSalidaDTO.class);
    }

    @Override
    public void eliminarPaciente(int id) {
        pacienteIDao.eliminarPorId(id);
    }

    private void configureMapping() {
        modelMapper.typeMap(PacienteEntradaDTO.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDTO::getDomicilioEntradaDTO, Paciente::setDomicilio));


        modelMapper.typeMap(Paciente.class, PacienteSalidaDTO.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDTO::setDomicilioSalidaDTO));

    }
}
