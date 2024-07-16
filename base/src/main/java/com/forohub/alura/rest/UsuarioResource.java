package com.forohub.alura.rest;

import com.forohub.alura.model.UsuarioDTO;
import com.forohub.alura.service.UsuarioService;
import com.forohub.alura.util.ReferencedException;
import com.forohub.alura.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {

    private final UsuarioService usuarioService;

    public UsuarioResource(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(usuarioService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final Long createdId = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateUsuario(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) {
        usuarioService.update(id, usuarioDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = usuarioService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
