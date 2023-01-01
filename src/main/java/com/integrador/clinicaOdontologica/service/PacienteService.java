package com.integrador.clinicaOdontologica.service;

import com.integrador.clinicaOdontologica.entity.Paciente;
import com.integrador.clinicaOdontologica.exception.ResourceNotFoundException;
import com.integrador.clinicaOdontologica.repository.PacienteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private PacienteRepository pacienteRepository;

    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardarPaciente (Paciente paciente) {
        LOGGER.info("Se inició una operación de guardado del paciente con apellido: "+
                paciente.getApellido());
        return pacienteRepository.save(paciente);
    }
    public void actualizarPaciente (Paciente paciente) {
        LOGGER.info("Se inició una operación de actualización del paciente con id = "+
                paciente.getId());
        pacienteRepository.save(paciente);
    }
    public void eliminarPaciente (Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteAEliminar = buscarPacientePorId(id);
        if (pacienteAEliminar.isPresent()){
            pacienteRepository.deleteById(id);
            LOGGER.warn("Se realizo una operación de eliminación del paciente con" +
                    " id = "+id);
        }
        else{
            throw new ResourceNotFoundException("El paciente con id = " + id + " no existe en la BD.");
        }

    }
    public Optional<Paciente> buscarPacientePorId (Long id) {
        LOGGER.info("Se inició una operación de búsqueda del paciente con id = "+id);
        return pacienteRepository.findById(id);
    }

    public List<Paciente> buscarTodosLosPacientes () {
        LOGGER.info("Se inició una operación de listado de odontologos");
        return pacienteRepository.findAll();
    }

}
