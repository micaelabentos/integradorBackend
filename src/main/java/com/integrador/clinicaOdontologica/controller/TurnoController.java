package com.integrador.clinicaOdontologica.controller;


import com.integrador.clinicaOdontologica.dto.TurnoDTO;
import com.integrador.clinicaOdontologica.exception.BadRequestException;
import com.integrador.clinicaOdontologica.exception.ResourceNotFoundException;
import com.integrador.clinicaOdontologica.service.OdontologoService;
import com.integrador.clinicaOdontologica.service.PacienteService;
import com.integrador.clinicaOdontologica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private TurnoService turnoService;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> buscarTodosLosTurnos() {
        return ResponseEntity.ok(turnoService.buscarTodosLosTurnos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoDTO> buscarTurno(@PathVariable("id") Long id) {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurno(id);
        if (turnoBuscado.isPresent()) {
            return ResponseEntity.ok(turnoBuscado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> registarTurno(@RequestBody TurnoDTO turno) throws BadRequestException {
        ResponseEntity<TurnoDTO> respuesta;

        if (pacienteService.buscarPacientePorId(turno.getPacienteId()).isPresent() &&
                odontologoService.buscarOdontologoPorId(turno.getOdontologoId()).isPresent()
        ) {
            respuesta = ResponseEntity.ok(turnoService.guardarTurno(turno));
        } else {

            throw new BadRequestException("No se puede registrar un turno si no existe" +
                    "un odontologo y/o un paciente");
        }
        return respuesta;

    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody TurnoDTO turno) {
        ResponseEntity<TurnoDTO> respuesta;
        if (turnoService.buscarTurno(turno.getId()).isPresent()) {
            if (pacienteService.buscarPacientePorId(turno.getPacienteId()).isPresent() &&
                    odontologoService.buscarOdontologoPorId(turno.getOdontologoId()).isPresent()
            ) {
                turnoService.actualizarTurno(turno);
                return ResponseEntity.ok("Se actualizó el turno con id = " + turno.getId());
            } else {
                return ResponseEntity.badRequest().body("Error al actualizar, verificar si el" +
                        " odontologo y/o el paciente existen en la base de datos.");
            }
        } else {
            return ResponseEntity.badRequest().body("No se puede actualizar ya que el turno no existe");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("Se eliminó el turno con ID " + id);
    }
}
