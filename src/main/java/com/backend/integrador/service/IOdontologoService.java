package com.backend.integrador.service;

import com.backend.integrador.dto.odontologo.OdontologoEntradaDTO;
import com.backend.integrador.dto.odontologo.OdontologoSalidaDTO;
import com.backend.integrador.entity.Odontologo;

import java.util.List;

public interface IOdontologoService {

    OdontologoSalidaDTO guardarOdontologo(OdontologoEntradaDTO odontologo);
    List<OdontologoSalidaDTO> buscarTodosLosOdontologos();
    OdontologoSalidaDTO buscarOdontologoPorId(int id);
    void eliminarOdontologo(int id);

    OdontologoSalidaDTO actualizarOdontologo(OdontologoEntradaDTO odontologo);
}
