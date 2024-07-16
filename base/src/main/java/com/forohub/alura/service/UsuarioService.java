package com.forohub.alura.service;


import com.forohub.alura.domain.Respuesta;
import com.forohub.alura.domain.Topic;
import com.forohub.alura.domain.Usuario;
import com.forohub.alura.model.UsuarioDTO;
import com.forohub.alura.repos.RespuestaRepository;
import com.forohub.alura.repos.TopicRepository;
import com.forohub.alura.repos.UsuarioRepository;
import com.forohub.alura.util.NotFoundException;
import com.forohub.alura.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RespuestaRepository respuestaRepository;
    private final TopicRepository topicRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository,
            final RespuestaRepository respuestaRepository, final TopicRepository topicRepository) {
        this.usuarioRepository = usuarioRepository;
        this.respuestaRepository = respuestaRepository;
        this.topicRepository = topicRepository;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("id"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getId();
    }

    public void update(final Long id, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setFechaCreacion(usuario.getFechaCreacion());
        usuarioDTO.setFechaActualizacion(usuario.getFechaActualizacion());
        usuarioDTO.setEstado(usuario.getEstado());
        usuarioDTO.setRole(usuario.getRole());
        return usuarioDTO;
    }


    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setContrasenia(usuarioDTO.getContrasenia());
        usuario.setEstado(usuarioDTO.getEstado());
        usuario.setRole(usuarioDTO.getRole());
        return usuario;
    }


    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Respuesta usuarioIdRespuesta = respuestaRepository.findFirstByUsuarioId(usuario);
        if (usuarioIdRespuesta != null) {
            referencedWarning.setKey("usuario.respuesta.usuarioId.referenced");
            referencedWarning.addParam(usuarioIdRespuesta.getId());
            return referencedWarning;
        }
        final Topic usuarioIdTopic = topicRepository.findFirstByUsuarioId(usuario);
        if (usuarioIdTopic != null) {
            referencedWarning.setKey("usuario.topic.usuarioId.referenced");
            referencedWarning.addParam(usuarioIdTopic.getId());
            return referencedWarning;
        }
        return null;
    }

}
