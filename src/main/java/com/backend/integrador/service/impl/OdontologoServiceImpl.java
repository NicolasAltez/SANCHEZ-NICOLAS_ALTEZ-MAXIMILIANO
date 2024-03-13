package com.backend.integrador.service.impl;


import com.backend.integrador.dto.odontologo.OdontologoEntradaDTO;
import com.backend.integrador.dto.odontologo.OdontologoSalidaDTO;
import com.backend.integrador.entity.Odontologo;
import com.backend.integrador.repository.OdontologoRepository;
import com.backend.integrador.service.IOdontologoService;
import com.backend.integrador.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoServiceImpl implements IOdontologoService {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceImpl.class);

    private OdontologoRepository odontologoRepository;
    private ModelMapper modelMapper;


    public OdontologoServiceImpl(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OdontologoSalidaDTO guardarOdontologo(OdontologoEntradaDTO odontologo) {
        LOGGER.info("OdontologoEntradaDTO {}", JsonPrinter.toString(odontologo));
        Odontologo odontologoGuardado = odontologoRepository.save(modelMapper.map(odontologo, Odontologo.class));
        LOGGER.info("OdontologoSAlidaDTO {}", odontologoGuardado);
        return modelMapper.map(odontologoGuardado, OdontologoSalidaDTO.class);
    }

    @Override
    public List<OdontologoSalidaDTO> buscarTodosLosOdontologos() {
        return odontologoRepository.findAll()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDTO.class))
                .toList();
    }

    @Override
    public OdontologoSalidaDTO buscarOdontologoPorId(Long id) {
        return odontologoRepository.findById(id).map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDTO.class))
                .orElseGet(() -> {
                    LOGGER.info("No se encontró el odontologo con id: {}", id);
                    return null;
                });
    }

    @Override
    public void eliminarOdontologo(Long id) {
       odontologoRepository.findById(id).ifPresentOrElse(
               odontologoRepository::delete,
               () -> LOGGER.info("No se encontró el odontologo a eliminar con id: {}", id)
       );
    }


    @Override
    public OdontologoSalidaDTO actualizarOdontologo(OdontologoEntradaDTO odontologo) {
        Optional<Odontologo> odontologoAActualizar = odontologoRepository.findByNumeroDeMatricula(odontologo.getNumeroDeMatricula());
        if (odontologoAActualizar.isPresent()){
            return modelMapper.map(odontologoAActualizar, OdontologoSalidaDTO.class);
        } else {
            LOGGER.info("No se encontró el odontologo a actualizar con matricula: {}", odontologo.getNumeroDeMatricula());
            return null;
        }
    }


}
