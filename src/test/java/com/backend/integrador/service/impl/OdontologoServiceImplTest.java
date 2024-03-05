package com.backend.integrador.service.impl;

import com.backend.integrador.dao.impl.OdontologoDaoH2;
import com.backend.integrador.entity.Odontologo;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class OdontologoServiceImplTest {

    private OdontologoServiceImpl odontologoService;

    @Test
    public void cuandoSeGuardaSatisfactoriamenteUnOdontologoDeberiaDevolverUnOdontologoConId(){
        odontologoService = new OdontologoServiceImpl(new OdontologoDaoH2());
        Odontologo odontologo = new Odontologo("AE2132132", "Nicolas","Sanchez");
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        assertNotNull(odontologoGuardado.getId());
    }

    @Test
    public void cuandoSeGuardaUnOdontologoConDatosNulosDeberiaDevolverUnOdontologoConIdNulo(){
        odontologoService = new OdontologoServiceImpl(new OdontologoDaoH2());
        Odontologo odontologo = new Odontologo(null, null,null);
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        assertNull(odontologoGuardado);
    }
    @Test
    public void cuandoSeBuscanTodosLosOdontologosDeberiaDevolverUnaListaConOdontologos(){
        odontologoService = new OdontologoServiceImpl(new OdontologoDaoH2());
        List<Odontologo> odontologoList = odontologoService.buscarTodosLosOdontologos();
        assertFalse(odontologoList.isEmpty());
    }
}
