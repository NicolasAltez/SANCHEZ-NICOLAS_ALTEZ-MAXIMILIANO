package com.backend.integrador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class IntegradorApplication {

    private static Logger LOGGER = LoggerFactory.getLogger(IntegradorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IntegradorApplication.class, args);
        crearBd();
        LOGGER.info("Aplicación corriendo");
    }

    private static void crearBd() {
        LOGGER.info("Creando base de datos");
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/odontologodb;INIT=RUNSCRIPT FROM 'script.sql'", "user", "password");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    LOGGER.info("Base de datos creada con exito, cerrando conexion");
                    connection.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }

    }
}
