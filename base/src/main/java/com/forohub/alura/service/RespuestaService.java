package com.forohub.alura.service;


import com.forohub.alura.domain.Respuesta;
import com.forohub.alura.domain.Topic;
import com.forohub.alura.domain.Usuario;
import com.forohub.alura.model.RespuestaDTO;
import com.forohub.alura.repos.RespuestaRepository;
import com.forohub.alura.repos.TopicRepository;
import com.forohub.alura.repos.UsuarioRepository;
import com.forohub.alura.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TopicRepository topicRepository;

    public RespuestaService(final RespuestaRepository respuestaRepository,
                            final UsuarioRepository usuarioRepository, TopicRepository topicRepository) {
        this.respuestaRepository = respuestaRepository;
        this.usuarioRepository = usuarioRepository;
       this.topicRepository = topicRepository;
    }

    public List<RespuestaDTO> findAll() {
        final List<Respuesta> respuestas = respuestaRepository.findAll(Sort.by("id"));
        return respuestas.stream()
                .map(respuesta -> mapToDTO(respuesta, new RespuestaDTO()))
                .toList();
    }

    public RespuestaDTO get(final Long id) {
        return respuestaRepository.findById(id)
                .map(respuesta -> mapToDTO(respuesta, new RespuestaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RespuestaDTO respuestaDTO) {
        final Respuesta respuesta = new Respuesta();
        mapToEntity(respuestaDTO, respuesta);
        return respuestaRepository.save(respuesta).getId();
    }

    public void update(final Long id, final RespuestaDTO respuestaDTO) {
        final Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(respuestaDTO, respuesta);
        respuestaRepository.save(respuesta);
    }

    public void delete(final Long id) {
        respuestaRepository.deleteById(id);
    }

    public boolean deleteIfExists(Long id) {
        if (respuestaRepository.existsById(id)) {
            respuestaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private RespuestaDTO mapToDTO(final Respuesta respuesta, final RespuestaDTO respuestaDTO) {
        respuestaDTO.setId(respuesta.getId());
        respuestaDTO.setMensajeRespuesta(respuesta.getMensajeRespuesta());
        respuestaDTO.setFechaCreacion(respuesta.getFechaCreacion());
        respuestaDTO.setFechaActualizacion(respuesta.getFechaActualizacion());
        respuestaDTO.setEstado(respuesta.getEstado());
        respuestaDTO.setUsuarioId(respuesta.getUsuario() != null ? respuesta.getUsuario().getId() : null);
        respuestaDTO.setTopic(respuesta.getTopic() != null ? respuesta.getTopic().getId() : null);
        return respuestaDTO;
    }

    private Respuesta mapToEntity(final RespuestaDTO respuestaDTO, final Respuesta respuesta) {
        respuesta.setMensajeRespuesta(respuestaDTO.getMensajeRespuesta());
        respuesta.setEstado(respuestaDTO.getEstado());


        if (respuestaDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(respuestaDTO.getUsuarioId())
                  .orElseThrow(() -> new NotFoundException("Usuario not found"));
            respuesta.setUsuario(usuario);
        } else {
            respuesta.setUsuario(null);
        }

        if (respuestaDTO.getTopic() != null) {
            Topic topic = topicRepository.findById(respuestaDTO.getTopic())
                  .orElseThrow(() -> new NotFoundException("Topic not found"));
            respuesta.setTopic(topic);
        } else {
            respuesta.setTopic(null);
        }

        return respuesta;
    }
}
