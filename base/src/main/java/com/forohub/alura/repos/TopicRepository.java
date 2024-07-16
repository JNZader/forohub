package com.forohub.alura.repos;


import com.forohub.alura.domain.Topic;
import com.forohub.alura.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Topic findFirstByUsuarioId(Usuario usuario);

    List<Topic> findAllByEstado(Boolean estado);

}
