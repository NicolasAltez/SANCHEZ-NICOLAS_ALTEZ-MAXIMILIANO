package com.backend.integrador.service.impl;

import com.backend.integrador.dao.IDao;
import com.backend.integrador.entity.Odontologo;
import com.backend.integrador.service.IOdontologoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoServiceImpl implements IOdontologoService {

    private IDao<Odontologo> odontologoIDao;

    public OdontologoServiceImpl(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoIDao.guardar(odontologo);
    }

    @Override
    public List<Odontologo> buscarTodosLosOdontologos() {
        return odontologoIDao.buscarTodos();
    }
}
