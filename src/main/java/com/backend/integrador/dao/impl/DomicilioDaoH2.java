package com.backend.integrador.dao.impl;

import com.backend.integrador.dao.IDao;
import com.backend.integrador.entity.Domicilio;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

@Component
public class DomicilioDaoH2 implements IDao<Domicilio> {

    private final Logger LOGGER = Logger.getLogger(DomicilioDaoH2.class.getName());

    @Override
    public Domicilio guardar(Domicilio domicilio) {
        return null;
    }

    @Override
    public List<Domicilio> buscarTodos() {
        return null;
    }

    @Override
    public Domicilio buscarPorId(int id) {
        return null;
    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) {
        return null;
    }

    @Override
    public void eliminarPorId(int id) {

    }
}
