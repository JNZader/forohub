package com.forohub.alura.domain;

import com.forohub.alura.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Set;


@Entity
@Table(name = "Usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nombre;

    @Column(length = 100)
    private String email;

    @Column
    private String contrasenia;

    @Column
    private LocalTime fechaCreacion;

    @Column
    private LocalTime fechaActualizacion;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean estado;

    @Column(name = "\"role\"")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "usuarioId")
    private Set<Respuesta> respuestas;

    @OneToMany(mappedBy = "usuarioId")
    private Set<Topic> temas;

}
