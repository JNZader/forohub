package com.forohub.alura.repos;

import com.forohub.alura.domain.Respuesta;
import com.forohub.alura.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    Respuesta findFirstByUsuarioId(Usuario usuario);

}
