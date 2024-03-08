package com.backend.integrador.dao.impl;

import com.backend.integrador.dao.IDao;
import com.backend.integrador.entity.Paciente;

import java.util.List;

public class PacienteDaoH2 implements IDao<Paciente> {
    @Override
    public Paciente guardar(Paciente paciente) {
        return null;
    }

    @Override
    public List<Paciente> buscarTodos() {
        return null;
    }

    @Override
    public Paciente buscarPorId(int id) {
        return null;
    }

    @Override
    public Paciente actualizar(Paciente paciente) {
        return null;
    }

    @Override
    public void eliminarPorId(int id) {

    }
}
