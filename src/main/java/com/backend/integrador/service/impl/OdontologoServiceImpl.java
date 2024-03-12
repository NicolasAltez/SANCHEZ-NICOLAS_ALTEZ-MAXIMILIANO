package com.backend.integrador.service.impl;

import com.backend.integrador.dao.IDao;
import com.backend.integrador.dto.odontologo.OdontologoEntradaDTO;
import com.backend.integrador.dto.odontologo.OdontologoSalidaDTO;
import com.backend.integrador.dto.paciente.PacienteSalidaDTO;
import com.backend.integrador.entity.Odontologo;
import com.backend.integrador.entity.Paciente;
import com.backend.integrador.service.IOdontologoService;
import com.backend.integrador.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoServiceImpl implements IOdontologoService {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceImpl.class);

    private IDao<Odontologo> odontologoIDao;
    private ModelMapper modelMapper;


    public OdontologoServiceImpl(IDao<Odontologo> odontologoIDao, ModelMapper modelMapper) {
        this.odontologoIDao = odontologoIDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public OdontologoSalidaDTO guardarOdontologo(OdontologoEntradaDTO odontologo) {
        LOGGER.info("OdontologoEntradaDTO {}", JsonPrinter.toString(odontologo));

        Odontologo odontologoGuardado = odontologoIDao.guardar(modelMapper.map(odontologo, Odontologo.class));

        LOGGER.info("OdontologoSAlidaDTO {}", odontologoGuardado);
        return modelMapper.map(odontologoGuardado, OdontologoSalidaDTO.class);
    }

    @Override
    public List<OdontologoSalidaDTO> buscarTodosLosOdontologos() {
        return odontologoIDao.buscarTodos().stream().map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDTO.class)).toList();
    }

    @Override
    public OdontologoSalidaDTO buscarOdontologoPorId(int id) {
        Odontologo odontologoBuscado = odontologoIDao.buscarPorId(id);
        if (odontologoBuscado != null) {
            return modelMapper.map(odontologoBuscado, OdontologoSalidaDTO.class);
        } else {
            LOGGER.info("No se encontró el odontologo con id: {}", id);
            return null;
        }
    }

    @Override
    public void eliminarOdontologo(int id) {
        odontologoIDao.eliminarPorId(id);
    }


    @Override
    public OdontologoSalidaDTO actualizarOdontologo(OdontologoEntradaDTO odontologo) {
        Odontologo odontologoActualizado = odontologoIDao.actualizar(modelMapper.map(odontologo, Odontologo.class));
        return modelMapper.map(odontologoActualizado, OdontologoSalidaDTO.class);
    }
}
