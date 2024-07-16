package com.forohub.alura.service;


import com.forohub.alura.domain.Topic;
import com.forohub.alura.domain.Usuario;
import com.forohub.alura.model.TopicDTO;
import com.forohub.alura.repos.TopicRepository;
import com.forohub.alura.repos.UsuarioRepository;
import com.forohub.alura.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UsuarioRepository usuarioRepository;

    public TopicService(final TopicRepository topicRepository,
            final UsuarioRepository usuarioRepository) {
        this.topicRepository = topicRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<TopicDTO> findAll() {
        final List<Topic> topics = topicRepository.findAll(Sort.by("id"));
        return topics.stream()
                .map(topic -> mapToDTO(topic, new TopicDTO()))
                .toList();
    }

    public List<TopicDTO> findAllByEstado(Boolean estado) {
        final List<Topic> topics = topicRepository.findAllByEstado(estado);

        return Optional.of(topics)
              .filter(list -> !list.isEmpty())
              .orElseThrow(() -> new NotFoundException("No se encontraron topics con el estado: " + estado))
              .stream()
              .map(topic -> mapToDTO(topic, new TopicDTO()))
              .toList();
    }

    public TopicDTO get(final Long id) {
        return topicRepository.findById(id)
                .map(topic -> mapToDTO(topic, new TopicDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TopicDTO topicDTO) {
        final Topic topic = new Topic();
        mapToEntity(topicDTO, topic);
        return topicRepository.save(topic).getId();
    }

    public void update(final Long id, final TopicDTO topicDTO) {
        final Topic topic = topicRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(topicDTO, topic);
        topicRepository.save(topic);
    }

    public void delete(final Long id) {
        topicRepository.deleteById(id);
    }

    public boolean deleteIfExists(Long id) {
        if (topicRepository.existsById(id)) {
            topicRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TopicDTO mapToDTO(final Topic topic, final TopicDTO topicDTO) {
        topicDTO.setId(topic.getId());
        topicDTO.setTitulo(topic.getTitulo());
        topicDTO.setMensaje(topic.getMensaje());
        topicDTO.setFechaCreacion(topic.getFechaCreacion());
        topicDTO.setFechaActualizacion(topic.getFechaActualizacion());
        topicDTO.setEstado(topic.getEstado());
        topicDTO.setGenero(topic.getGenero());
        topicDTO.setUsuarioId(topic.getUsuario() != null ? topic.getUsuario().getId() : null);
        return topicDTO;
    }


    private Topic mapToEntity(final TopicDTO topicDTO, final Topic topic) {
        topic.setTitulo(topicDTO.getTitulo());
        topic.setMensaje(topicDTO.getMensaje());

        topic.setEstado(topicDTO.getEstado());
        topic.setGenero(topicDTO.getGenero());

        if (topicDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(topicDTO.getUsuarioId())
                  .orElseThrow(() -> new NotFoundException("Usuario not found"));
            topic.setUsuario(usuario);
        } else {
            topic.setUsuario(null);
        }

        return topic;
    }

}
