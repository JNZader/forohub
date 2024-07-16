package com.forohub.alura.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mensajeRespuesta;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column
    private LocalDateTime fechaActualizacion;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;
}