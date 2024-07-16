package com.forohub.alura.util;

import com.forohub.alura.domain.Usuario;
import com.forohub.alura.model.Role;
import com.forohub.alura.repos.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * Clase que inicializa un usuario administrador en la base de datos si no
 * existe.
 */
@Component
public class AdminUserInitializer {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${security.user.name}")
    private String adminUsername;

    @Value("${security.user.password}")
    private String adminPassword;

    /**
     * Constructor de la clase AdminUserInitializer.
     *
     * @param usuarioRepository Repositorio de usuarios.
     * @param passwordEncoder Codificador de contraseñas.
     */
    @Autowired
    public AdminUserInitializer(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método ejecutado después de la construcción del bean para crear el
     * usuario administrador si no existe.
     */
    @PostConstruct
    public void createAdminUser() {
        // Verifica si el usuario administrador ya existe en la base de datos
        if (usuarioRepository.findBynombre(adminUsername).isEmpty()) {
            // Si no existe, crea un nuevo usuario administrador
            Usuario admin = Usuario.builder()
                    .nombre(adminUsername)
                    .contrasenia(passwordEncoder.encode(adminPassword))
                    .role(Role.ADMIN)
                    .build();

            // Guarda el usuario administrador en la base de datos
            usuarioRepository.save(admin);
        }
    }
}
