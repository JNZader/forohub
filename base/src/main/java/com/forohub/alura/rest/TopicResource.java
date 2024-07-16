package com.forohub.alura.rest;

import com.forohub.alura.model.TopicDTO;
import com.forohub.alura.service.TopicService;
import com.forohub.alura.util.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/topics", produces = MediaType.APPLICATION_JSON_VALUE)
public class TopicResource {

    private final TopicService topicService;

    public TopicResource(final TopicService topicService) {
        this.topicService = topicService;
    }


    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAllTopics(@RequestParam(required = false) Boolean estado) {
        try {
            List<TopicDTO> topics;
            if (estado != null) {
                topics = topicService.findAllByEstado(estado);
            } else {
                topics = topicService.findAll();
            }
            return ResponseEntity.ok(topics);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> getTopic(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(topicService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createTopic(@RequestBody @Valid final TopicDTO topicDTO) {
        final Long createdId = topicService.create(topicDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTopic(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TopicDTO topicDTO) {
        topicService.update(id, topicDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable(name = "id") final Long id) {
        try {
            boolean deleted = topicService.deleteIfExists(id);
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
