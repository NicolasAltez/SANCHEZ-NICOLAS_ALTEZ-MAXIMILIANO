package com.backend.integrador.dao.impl;

import com.backend.integrador.dao.IDao;
import com.backend.integrador.dbconnection.H2Connection;
import com.backend.integrador.entity.Odontologo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class OdontologoDaoH2 implements IDao<Odontologo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OdontologoDaoH2.class);

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoRegistrado = null;

        try {

            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ODONTOLOGOS (numero_de_matricula, nombre, apellido) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getNumeroDeMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());

            preparedStatement.execute();


            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                odontologoRegistrado = new Odontologo(resultSet.getInt(1), odontologo.getNumeroDeMatricula(), odontologo.getNombre(), odontologo.getApellido());//se puede comprimir
            }

            connection.commit();

            if (odontologoRegistrado != null) {
                LOGGER.info("Odontologo registrado con éxito: {}", odontologoRegistrado);
            }


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (existeLaConexion(connection)) {
                try {
                    connection.rollback();
                    LOGGER.info("Se hizo rollback de la transaccion ya que hubo un error al registrar el odontologo");
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
        return odontologoRegistrado;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();

        try {
            connection = H2Connection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ODONTOLOGOS");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                odontologos.add(crearOdontologo(resultSet));
            }

            if (!odontologos.isEmpty()) {
                LOGGER.info("Se encontraron los siguientes odontólogos: {}", odontologos);
            } else {
                LOGGER.error("No se encontraron odontólogos");
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
        return odontologos;
    }

    @Override
    public Odontologo buscarPorId(int id) {
        Connection connection = null;
        Odontologo odontologoEncontrado = null;
        try {
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ODONTOLOGOS WHERE id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                odontologoEncontrado = crearOdontologo(resultSet);
            }

            if (odontologoEncontrado == null) LOGGER.error("No se encontró el odontólogo con id: {}", id);
            else LOGGER.info("Se encontró el odontólogo con id: {} {}", id, odontologoEncontrado);

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
        return odontologoEncontrado;
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoActualizado = null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ODONTOLOGOS SET NUMERO_DE_MATRICULA = ?, NOMBRE = ?, APELLIDO = ? WHERE ID = ?");
            preparedStatement.setString(1, odontologo.getNumeroDeMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.setInt(4, odontologo.getId());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                connection.commit();
                odontologoActualizado = odontologo;
                LOGGER.info("Odontologo actualizado con exito: {} ", odontologoActualizado);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (existeLaConexion(connection)) {
                try {
                    connection.rollback();
                    LOGGER.info("Se hizo rollback de la transaccion ya que hubo un error al actualizar el odontologo");
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

        return odontologoActualizado;
    }

    @Override
    public void eliminarPorId(int id) {
        Connection connection = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ODONTOLOGOS WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            connection.commit();


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (existeLaConexion(connection)) {
                try {
                    connection.rollback();
                    LOGGER.info("Se hizo rollback de la transaccion ya que hubo un error al borrar el odontologo");
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

    private Odontologo crearOdontologo(ResultSet resultSet) throws SQLException {
        return new Odontologo(resultSet.getInt("id"), resultSet.getString("numero_de_matricula"), resultSet.getString("nombre"), resultSet.getString("apellido"));
    }
}
