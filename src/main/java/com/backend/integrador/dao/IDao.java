package com.backend.integrador.dao;

import java.util.List;

public interface IDao <T>{
    T guardar(T t);
    List<T> buscarTodos();
    T buscarPorId(int id);
    T actualizar(T t);
}
