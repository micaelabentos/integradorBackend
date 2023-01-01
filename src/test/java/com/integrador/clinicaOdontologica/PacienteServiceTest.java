package com.integrador.clinicaOdontologica;

import com.integrador.clinicaOdontologica.entity.Domicilio;
import com.integrador.clinicaOdontologica.entity.Paciente;
import com.integrador.clinicaOdontologica.exception.ResourceNotFoundException;
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
public class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarPacienteTest(){
        Paciente pacienteAGuardar= new Paciente("Micaela","Bentos"
                ,"53639996", LocalDate.of(2022,11,15),"test@test.com",
                new Domicilio("18 de julio",111,"Montevideo","Montevideo"));
        Paciente paciente = pacienteService.guardarPaciente(pacienteAGuardar);
        assertEquals(1L,paciente.getId());
    }

    @Test
    @Order(2)
    public void buscarPacientePorIdTest(){
        Long idABuscar = 1L;
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(idABuscar);
        assertNotNull(pacienteBuscado.get());
    }

    @Test
    @Order(3)
    public void buscarPacientesTest(){
        List<Paciente> pacientes = pacienteService.buscarTodosLosPacientes();
        Integer cantidadEsperada = 1;
        assertEquals(cantidadEsperada,pacientes.size());
    }

    @Test
    @Order(4)
    public void actualizarPacienteTest(){
        Paciente pacienteAActualizar= new Paciente(1L,"Ana","Bentos"
                ,"53639996", LocalDate.of(2022,11,15),"test@test.com",
                new Domicilio(1L,"18 de julio",111,"Montevideo","Montevideo"));

        pacienteService.actualizarPaciente(pacienteAActualizar);
        Optional<Paciente> pacienteActualizado= pacienteService.buscarPacientePorId(pacienteAActualizar.getId());
        assertEquals("Ana",pacienteActualizado.get().getNombre());
    }
    @Test
    @Order(5)
    public void eliminarPacienteTest() throws ResourceNotFoundException {

        Long idAEliminar = 1L;
        pacienteService.eliminarPaciente(idAEliminar);
        Optional<Paciente> pacienteEliminado=pacienteService.buscarPacientePorId(idAEliminar);
        assertFalse(pacienteEliminado.isPresent());
    }
}
