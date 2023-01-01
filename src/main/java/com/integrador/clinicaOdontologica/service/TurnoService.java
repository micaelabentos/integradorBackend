package com.integrador.clinicaOdontologica.service;

import com.integrador.clinicaOdontologica.dto.TurnoDTO;
import com.integrador.clinicaOdontologica.entity.Odontologo;
import com.integrador.clinicaOdontologica.entity.Paciente;
import com.integrador.clinicaOdontologica.entity.Turno;
import com.integrador.clinicaOdontologica.exception.ResourceNotFoundException;
import com.integrador.clinicaOdontologica.repository.TurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    private TurnoRepository turnoRepository;

    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    @Autowired
    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public TurnoDTO guardarTurno (TurnoDTO turno){
        LOGGER.info("Se inició una operación de guardado del turno");
        Turno turnoAGuardar=turnoDTOaTurno(turno);
        Turno turnoGuardado=turnoRepository.save(turnoAGuardar);
        return turnoATurnoDTO(turnoGuardado);
    }

    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoAEliminar = buscarTurno(id);
        if (turnoAEliminar.isPresent()){
            turnoRepository.deleteById(id);
            LOGGER.warn("Se realizo una operación de eliminación del turno con" +
                    " id = "+id);
        }
        else{
            throw new ResourceNotFoundException("El turno con id = " + id + " no existe en la BD.");
        }
    }

    public void actualizarTurno(TurnoDTO turno){
        LOGGER.info("Se inició una operación de actualización del turno con id = "+
                turno.getId());
        Turno turnoAActualizar=turnoDTOaTurno(turno);
        turnoRepository.save(turnoAActualizar);
    }

    public Optional<TurnoDTO> buscarTurno(Long id){
        LOGGER.info("Se inició una operación de búsqueda del turno con id = "+id);
        Optional<Turno> turnoBuscado=turnoRepository.findById(id);
        if (turnoBuscado.isPresent()){
            return Optional.of(turnoATurnoDTO(turnoBuscado.get()));
        }
        else{
            return Optional.empty();
        }
    }
    public List<TurnoDTO> buscarTodosLosTurnos(){
        LOGGER.info("Se inició una operación de listado de turnos");
        List<Turno>turnosEncontrados=turnoRepository.findAll();
        List<TurnoDTO> respuesta= new ArrayList<>();
        for (Turno t:turnosEncontrados) {
            respuesta.add(turnoATurnoDTO(t));
        }
        return respuesta;
    }
    private TurnoDTO turnoATurnoDTO(Turno turno){
        TurnoDTO respuesta=new TurnoDTO();
        respuesta.setId(turno.getId());
        respuesta.setFecha(turno.getFecha());
        respuesta.setOdontologoId(turno.getOdontologo().getId());
        respuesta.setPacienteId(turno.getPaciente().getId());
        return respuesta;
    }
    private Turno turnoDTOaTurno(TurnoDTO turnoDTO){
        Turno turno= new Turno();
        Paciente paciente= new Paciente();
        Odontologo odontologo= new Odontologo();
        paciente.setId(turnoDTO.getPacienteId());
        odontologo.setId(turnoDTO.getOdontologoId());
        turno.setId(turnoDTO.getId());
        turno.setFecha(turnoDTO.getFecha());
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        return turno;
    }
}
