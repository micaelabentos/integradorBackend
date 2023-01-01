package com.integrador.clinicaOdontologica;

import com.integrador.clinicaOdontologica.dto.TurnoDTO;
import com.integrador.clinicaOdontologica.entity.Domicilio;
import com.integrador.clinicaOdontologica.entity.Odontologo;
import com.integrador.clinicaOdontologica.entity.Paciente;
import com.integrador.clinicaOdontologica.exception.ResourceNotFoundException;
import com.integrador.clinicaOdontologica.service.OdontologoService;
import com.integrador.clinicaOdontologica.service.PacienteService;
import com.integrador.clinicaOdontologica.service.TurnoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest

public class TurnoServiceTest {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;

    @Test
    @Order(1)
    public void guardarTurnoTest(){

        Odontologo odontologoAGuardar= new Odontologo("1111", "Micaela", "Bentos");
        Odontologo odontologo = odontologoService.guardarOdontologo(odontologoAGuardar);

        Paciente pacienteAGuardar= new Paciente("Micaela","Bentos"
                ,"53639996", LocalDate.of(2022,11,15),"test@test.com",
                new Domicilio("18 de julio",111,"Montevideo","Montevideo"));
        Paciente paciente = pacienteService.guardarPaciente(pacienteAGuardar);

        TurnoDTO turnoAGuardar = new TurnoDTO();
        turnoAGuardar.setPacienteId(1L);
        turnoAGuardar.setOdontologoId(1L);
        turnoAGuardar.setFecha(LocalDate.of(2022,12,29));
        TurnoDTO turno = turnoService.guardarTurno(turnoAGuardar);
        assertEquals(1L,turno.getId());
    }

    @Test
    @Order(2)
    public void buscarTurnoPorIdTest(){
        Long idABuscar = 1L;
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurno(idABuscar);
        assertNotNull(turnoBuscado.get());
    }

    @Test
    @Order(3)
    public void buscarTurnosTest(){
        List<TurnoDTO> turnos = turnoService.buscarTodosLosTurnos();
        Integer cantidadEsperada = 1;
        assertEquals(cantidadEsperada,turnos.size());
    }

    @Test
    @Order(4)
    public void actualizarTurnoTest(){
        Odontologo odontologoAGuardar= new Odontologo("1111", "Micaela", "Bentos");
        Odontologo odontologo = odontologoService.guardarOdontologo(odontologoAGuardar);

        Paciente pacienteAGuardar= new Paciente("Micaela","Bentos"
                ,"53639996", LocalDate.of(2022,11,15),"test@test.com",
                new Domicilio("18 de julio",111,"Montevideo","Montevideo"));
        Paciente paciente = pacienteService.guardarPaciente(pacienteAGuardar);

        TurnoDTO turnoAActualizar= new TurnoDTO();
        turnoAActualizar.setId(1L);
        turnoAActualizar.setPacienteId(pacienteAGuardar.getId());
        turnoAActualizar.setOdontologoId( (odontologoAGuardar.getId()));
        turnoAActualizar.setFecha(LocalDate.of(2022,11,12));
        turnoService.actualizarTurno(turnoAActualizar);
        Optional<TurnoDTO> turnoAcutalizado = turnoService.buscarTurno(turnoAActualizar.getId());
        assertEquals(LocalDate.of(2022,11,12),turnoAcutalizado.get().getFecha());
    }

    @Test
    @Order(5)
    public void eliminarTurnoTest() throws ResourceNotFoundException {

        Long idAEliminar = 1L;
        turnoService.eliminarTurno(idAEliminar);
        Optional<TurnoDTO> turnoEliminado =turnoService.buscarTurno(idAEliminar);
        assertFalse(turnoEliminado.isPresent());
    }
}
