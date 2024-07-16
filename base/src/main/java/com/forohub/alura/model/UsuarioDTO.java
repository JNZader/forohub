package com.forohub.alura.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;


@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    @Size(max = 50)
    private String nombre;

    @Size(max = 100)
    private String email;

    @Size(max = 255)
    private String contrasenia;

    private LocalTime fechaCreacion;

    private LocalTime fechaActualizacion;

    private Boolean estado;

    private Role role;

}
