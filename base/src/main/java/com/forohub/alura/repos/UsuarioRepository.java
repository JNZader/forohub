package com.forohub.alura.repos;


import com.forohub.alura.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

   Optional<Usuario> findById(Long id);

   Optional<Usuario> findBynombre(String username);
}
