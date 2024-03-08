package com.backend.integrador.dao.impl;

import com.backend.integrador.dao.IDao;
import com.backend.integrador.dbconnection.H2Connection;
import com.backend.integrador.entity.Domicilio;
import com.backend.integrador.entity.Paciente;

import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

@Component
public class PacienteDaoH2 implements IDao<Paciente> {

    private final Logger LOGGER = LoggerFactory.getLogger(DomicilioDaoH2.class.getName());

    @Override
    public Paciente guardar(Paciente paciente) {
        Connection connection = null;
        Paciente pacienteRegistrado = null;

        try {

            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PACIENTES (nombre, apellido, dni, fechaIngreso, domicilio) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setString(2, paciente.getApellido());
            preparedStatement.setInt(3, paciente.getDni());
            preparedStatement.setString(4, paciente.getFechaIngreso());//del local date
            preparedStatement.setInt(5, paciente.getDomicilio());//seria el id de la tabla domicilio

            preparedStatement.execute();


            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                pacienteRegistrado = crearPaciente(resultSet);// ver si queda bien
            }

            connection.commit();

            if (pacienteRegistrado != null) {
                LOGGER.info("Paciente registrado con éxito: {}", pacienteRegistrado);
            }


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (existeLaConexion(connection)) {
                try {
                    connection.rollback();
                    LOGGER.info("Se hizo rollback de la transaccion ya que hubo un error al registrar el paciente");
                } catch (SQLException ex) {
                    LOGGER.error(ex.getMessage());
                }
            }
        } finally {
            if (existeLaConexion(connection)) {
                try {
                    connection.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return pacienteRegistrado;
    }

    @Override
    public List<Paciente> buscarTodos() {
        Connection connection = null;
        List<Paciente> pacientes = new ArrayList<>();

        try {
            connection = H2Connection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PACIENTES");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pacientes.add(crearPaciente(resultSet));
            }

            if (!pacientes.isEmpty()) {
                LOGGER.info("Se encontraron los siguientes pacientes: {}", pacientes);
            } else {
                LOGGER.error("No se encontraron pacientes");
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (existeLaConexion(connection)) {
                try {
                    connection.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return pacientes;
    }

    @Override
    public Paciente buscarPorId(int id) {
        return null;
    }

    @Override
    public Paciente actualizar(Paciente paciente) {
        Connection connection = null;
        Paciente pacienteActualizado = null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PACIENTES SET nombre = ?, apellido = ?, dni = ?, fechaIngreso = ?, domicilio = ? WHERE ID = ?");
            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setString(2, paciente.getApellido());
            preparedStatement.setInt(3, paciente.getDni());
            preparedStatement.setString(4, paciente.getFechaIngreso());//del local date
            preparedStatement.setInt(5, paciente.getDomicilio());//seria el id de la tabla domicilio

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                connection.commit();
                pacienteActualizado = paciente;
                LOGGER.info("Paciente actualizado con exito: {} ", pacienteActualizado);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (existeLaConexion(connection)) {
                try {
                    connection.rollback();
                    LOGGER.info("Se hizo rollback de la transaccion ya que hubo un error al actualizar el paciente");
                } catch (SQLException ex) {
                    LOGGER.error(ex.getMessage());
                }
            }
        } finally {
            if (existeLaConexion(connection)) {
                try {
                    connection.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }

        return pacienteActualizado;
    }

    @Override
    public void eliminarPorId(int id) {
        Connection connection = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PACIENTES WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            connection.commit();


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (existeLaConexion(connection)) {
                try {
                    connection.rollback();
                    LOGGER.info("Se hizo rollback de la transaccion ya que hubo un error al borrar el paciente");
                } catch (SQLException ex) {
                    LOGGER.error(ex.getMessage());
                }
            }
        } finally {
            if (existeLaConexion(connection)) {
                try {
                    connection.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

    private boolean existeLaConexion(Connection connection) {
        return connection != null;
    }

    private Paciente crearPaciente(ResultSet resultSet) throws SQLException {
        return new Paciente(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("apellido"), resultSet.getInt("dni"), resultSet.getLocalDate("fechaIngreso"), resultSet.getDomicilio("domicilio"));
    }
}
