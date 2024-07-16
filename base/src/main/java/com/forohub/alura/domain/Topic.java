package com.forohub.alura.domain;

import com.forohub.alura.model.Genero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Set;


@Entity
@Table(name = "Topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 2550)
    private String mensaje;

    @Column
    private LocalTime fechaCreacion;

    @Column
    private LocalTime fechaActualizacion;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean estado;

    @Column
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioId;

    @OneToMany(mappedBy = "topic")
    private Set<Respuesta> respuestas;
}
