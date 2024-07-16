package com.forohub.alura.domain;

import com.forohub.alura.model.Genero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "topics")
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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column
    private LocalDateTime fechaActualizacion;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean estado;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "topic")
    private Set<Respuesta> respuestas;
}