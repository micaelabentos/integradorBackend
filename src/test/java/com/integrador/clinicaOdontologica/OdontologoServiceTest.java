package com.integrador.clinicaOdontologica;

import com.integrador.clinicaOdontologica.entity.Domicilio;
import com.integrador.clinicaOdontologica.entity.Odontologo;
import com.integrador.clinicaOdontologica.entity.Paciente;
import com.integrador.clinicaOdontologica.exception.ResourceNotFoundException;
import com.integrador.clinicaOdontologica.service.OdontologoService;
import com.integrador.clinicaOdontologica.service.PacienteService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class OdontologoServiceTest {
    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologoTest(){
        Odontologo odontologoAGuardar= new Odontologo("1111", "Micaela", "Bentos");
        Odontologo odontologo = odontologoService.guardarOdontologo(odontologoAGuardar);
        assertEquals(1L,odontologo.getId());
    }

    @Test
    @Order(2)
    public void buscarOdontologoPorIdTest(){
        Long idABuscar = 1L;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(idABuscar);
        assertNotNull(odontologoBuscado.get());
    }

    @Test
    @Order(3)
    public void buscarOdontologosTest(){
        List<Odontologo> odontologos = odontologoService.buscarTodosLosOdontologos();
        Integer cantidadEsperada = 1;
        assertEquals(cantidadEsperada,odontologos.size());
    }

    @Test
    @Order(4)
    public void actualizarOdontologoTest(){
        Odontologo odontologoAActualizar= new Odontologo(1L,"1111", "Micaela", "Bueno");

        odontologoService.actualizarOdontologo(odontologoAActualizar);
        Optional<Odontologo> odontologoActualizado = odontologoService.buscarOdontologoPorId(odontologoAActualizar.getId());
        assertEquals("Bueno",odontologoActualizado.get().getApellido());
    }
    @Test
    @Order(5)
    public void eliminarPacienteTest() throws ResourceNotFoundException {

        Long idAEliminar = 1L;
        odontologoService.eliminarOdontologo(idAEliminar);
        Optional<Odontologo> odontologoEliminado =odontologoService.buscarOdontologoPorId(idAEliminar);
        assertFalse(odontologoEliminado.isPresent());
    }
 }
