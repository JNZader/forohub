package com.forohub.alura.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    @Pattern(regexp = "^[A-Za-záéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo puede contener letras y espacios.")
    @Size(max = 50)
    private String nombre;

    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 8, max = 255)
    private String contrasenia;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    @NotNull
    private Boolean estado;

    private Role role;
}