package com.backend.integrador.dao.impl;

import com.backend.integrador.dao.IDao;
import com.backend.integrador.dbconnection.H2Connection;
import com.backend.integrador.entity.Domicilio;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Logger;// es este el que hay que importar?

@Component
public class DomicilioDaoH2 implements IDao<Domicilio> {

    private final Logger LOGGER = LoggerFactory.getLogger(DomicilioDaoH2.class.getName());

    @Override
    public Domicilio guardar(Domicilio domicilio) {

        Connection connection = null;
        Domicilio domicilioRegistrado = null;

        try {

            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO DOMICILIOS (calle, numero, localidad, provincia) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, domicilio.getCalle());
            preparedStatement.setInt(2, domicilio.getNumero());// seria un int, no se si quedo bien
            preparedStatement.setString(3, domicilio.getLocalidad());
            preparedStatement.setString(4, domicilio.getProvincia());

            preparedStatement.execute();


            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                domicilioRegistrado = new Domicilio(resultSet.getInt(1),  domicilio.getCalle(), domicilio.getNumero(), domicilio.getLocalidad(), domicilio.getProvincia());
            }

            connection.commit();

            if (domicilioRegistrado != null) {
                LOGGER.info("Domicilio registrado con éxito: {}");//, domicilioRegistrado);
            }


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (existeLaConexion(connection)) {
                try {
                    connection.rollback();
                    LOGGER.info("Se hizo rollback de la transaccion ya que hubo un error al registrar el domicilio");
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
        return domicilioRegistrado;
    }

    @Override
    public List<Domicilio> buscarTodos() {
        Connection connection = null;
        List<Domicilio> domicilios = new ArrayList<>();

        try {
            connection = H2Connection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM DOMICILIOS");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                domicilios.add(crearDomicilio(resultSet));
            }

            if (!domicilios.isEmpty()) {
                LOGGER.info("Se encontraron los siguientes domicilios: {}", domicilios);
            } else {
                LOGGER.error("No se encontraron domicilios");
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
        return domicilios;
    }

    @Override
    public Domicilio buscarPorId(int id) {
        Connection connection = null;
        Domicilio domicilioEncontrado = null;
        try {
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM DOMICILIOS WHERE id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                domicilioEncontrado = crearDomicilio(resultSet);
            }

            if (domicilioEncontrado == null) LOGGER.error("No se encontró el domicilio con id: {}", id);
            else LOGGER.info("Se encontró el domicilio con id: {} {}", id, domicilioEncontrado);

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
        return domicilioEncontrado;
    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) {
        Connection connection = null;
        Domicilio domicilioActualizado = null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE DOMICILIOS SET CALLE = ?, NUMERO = ?, LOCALIDAD = ?, PROVINCIA = ? WHERE ID = ?");
            preparedStatement.setString(1, domicilio.getCalle());
            preparedStatement.setInt(2, domicilio.getNumero());
            preparedStatement.setString(3, domicilio.getLocalidad());
            preparedStatement.setString(4, domicilio.getProvincia());
            preparedStatement.setInt(5, domicilio.getId());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                connection.commit();
                domicilioActualizado = domicilio;
                LOGGER.info("Domicilio actualizado con exito: {} ", domicilioActualizado);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (existeLaConexion(connection)) {
                try {
                    connection.rollback();
                    LOGGER.info("Se hizo rollback de la transaccion ya que hubo un error al actualizar el domicilio");
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

        return domicilioActualizado;
    }

    @Override
    public void eliminarPorId(int id) {
        Connection connection = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM DOMICILIOS WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            connection.commit();


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (existeLaConexion(connection)) {
                try {
                    connection.rollback();
                    LOGGER.info("Se hizo rollback de la transaccion ya que hubo un error al borrar el domicilio");
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

    private Domicilio crearDomicilio(ResultSet resultSet) throws SQLException {
        return new Domicilio(resultSet.getInt("id"),  resultSet.getString("calle"), resultSet.getInt("numero"), resultSet.getString("localidad"), resultSet.getString("provincia"));
    }
}
