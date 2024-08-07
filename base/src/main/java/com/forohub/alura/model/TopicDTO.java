package com.forohub.alura.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TopicDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String titulo;

    @NotNull
    @Size(max = 2550)
    private String mensaje;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    private Boolean estado;

    private Genero genero;

    private Long usuarioId;

}
