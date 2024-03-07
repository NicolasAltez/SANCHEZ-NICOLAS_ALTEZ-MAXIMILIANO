package com.backend.integrador.service;

import com.backend.integrador.entity.Odontologo;

import java.util.List;

public interface IOdontologoService {

    Odontologo guardarOdontologo(Odontologo odontologo);
    List<Odontologo> buscarTodosLosOdontologos();
    Odontologo buscarOdontologoPorId(int id);

    Odontologo actualizarOdontologo(Odontologo odontologo);
}
