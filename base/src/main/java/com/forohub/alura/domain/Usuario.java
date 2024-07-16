package com.forohub.alura.domain;

import com.forohub.alura.model.LoginRequest;
import com.forohub.alura.model.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nombre;

    @Column(length = 100, unique = true)
    private String email;

    @Column
    private String contrasenia;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column
    private LocalDateTime fechaActualizacion;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean estado;

    @Column(name = "\"role\"")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "usuario")
    private Set<Respuesta> respuestas;

    @OneToMany(mappedBy = "usuario")
    private Set<Topic> temas;

    public boolean isLoginCorrect(LoginRequest loginRequest, BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.contrasenia);
    }
}