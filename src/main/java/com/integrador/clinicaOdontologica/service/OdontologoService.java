package com.integrador.clinicaOdontologica.service;

import com.integrador.clinicaOdontologica.entity.Odontologo;
import com.integrador.clinicaOdontologica.exception.ResourceNotFoundException;
import com.integrador.clinicaOdontologica.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    private OdontologoRepository odontologoRepository;

    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardarOdontologo (Odontologo odontologo) {
        LOGGER.info("Se inició una operación de guardado del odontologo con apellido: "+
                odontologo.getApellido());
        return odontologoRepository.save(odontologo);
    }
    public void actualizarOdontologo (Odontologo odontologo) {
        LOGGER.info("Se inició una operación de actualización del odontologo con id = "+
                odontologo.getId());
        odontologoRepository.save(odontologo);
    }
    public void eliminarOdontologo (Long id)  throws ResourceNotFoundException {
        Optional<Odontologo> odontologoAEliminar = buscarOdontologoPorId(id);
        if (odontologoAEliminar.isPresent()){
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se realizo una operación de eliminación del odontologo con" +
                    " id = "+id);
        }
        else{
            throw new ResourceNotFoundException("El odontologo con id = " + id + " no existe en la BD.");
        }

    }

    public Optional<Odontologo> buscarOdontologoPorId (Long id) {
        LOGGER.info("Se inició una operación de búsqueda del odontologo con id = "+id);
        return odontologoRepository.findById(id);
    }

    public List<Odontologo> buscarTodosLosOdontologos () {
        LOGGER.info("Se inició una operación de listado de odontologos");
            return odontologoRepository.findAll();
    }
}
