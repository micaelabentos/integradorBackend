package com.integrador.clinicaOdontologica.security;


import com.integrador.clinicaOdontologica.entity.Usuario;
import com.integrador.clinicaOdontologica.entity.UsuarioRole;
import com.integrador.clinicaOdontologica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CargarDatosIniciales implements ApplicationRunner {
    private UsuarioRepository usuarioRepository;
    @Autowired
    public CargarDatosIniciales(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        BCryptPasswordEncoder cifrador = new BCryptPasswordEncoder();
        String passCifrada = cifrador.encode("12345");
        Usuario usuario = new Usuario("Micaela","Mica","bentosmicaela@gmail.com",
                passCifrada, UsuarioRole.ROLE_USER);
        usuarioRepository.save(usuario);

        usuario = new Usuario("User","User","user@test.com",
                passCifrada, UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuario);
    }
}
