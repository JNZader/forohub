package com.forohub.alura.rest;

import com.forohub.alura.model.RespuestaDTO;
import com.forohub.alura.service.RespuestaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/respuestas", produces = MediaType.APPLICATION_JSON_VALUE)
public class RespuestaResource {

    private final RespuestaService respuestaService;

    public RespuestaResource(final RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @GetMapping
    public ResponseEntity<List<RespuestaDTO>> getAllRespuestas() {
        return ResponseEntity.ok(respuestaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO> getRespuesta(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(respuestaService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createRespuesta(
            @RequestBody @Valid final RespuestaDTO respuestaDTO) {
        final Long createdId = respuestaService.create(respuestaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateRespuesta(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final RespuestaDTO respuestaDTO) {
        respuestaService.update(id, respuestaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRespuesta(@PathVariable(name = "id") final Long id) {
        try {
            boolean deleted = respuestaService.deleteIfExists(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
