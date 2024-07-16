package com.forohub.alura.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RespuestaDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String mensajeRespuesta;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    private Boolean estado;

    private Long usuarioId;

    private Long topic;

}
