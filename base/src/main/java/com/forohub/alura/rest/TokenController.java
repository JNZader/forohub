package com.forohub.alura.rest;

import com.forohub.alura.model.CambiarPassDTO;
import com.forohub.alura.model.LoginRequest;
import com.forohub.alura.model.LoginResponse;
import com.forohub.alura.repos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.time.Instant;

@RestController
@EnableMethodSecurity
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public TokenController(JwtEncoder jwtEncoder, UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Maneja la solicitud de inicio de sesión y devuelve un token JWT si las
     * credenciales son válidas.
     *
     * @param loginRequest Objeto que contiene el nombre de usuario y la
     * contraseña.
     * @return ResponseEntity con la respuesta de inicio de sesión, incluido el
     * token JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        var user = usuarioRepository.findBynombre(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Usuario o contraseña invalido");
        }


        var now = Instant.now();
        var expiresIn = 3600L;

        var scope = user.get().getRole();

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scope)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

    /**
     * Maneja la solicitud de cambio de contraseña.
     *
     * @param request Objeto que contiene el nombre de usuario, la contraseña
     * actual y la nueva contraseña.
     * @return ResponseEntity con la respuesta del cambio de contraseña.
     */
    @PostMapping("/cambiar-pass")
    public ResponseEntity<Void> cambiarPass(@RequestBody CambiarPassDTO request) {
        var user = usuarioRepository.findBynombre(request.username()).orElseThrow();

        if (!passwordEncoder.matches(request.password(), user.getContrasenia())) {
            throw new BadCredentialsException("Contraseña invalida");
        }

        user.setContrasenia(passwordEncoder.encode(request.newPass()));
        usuarioRepository.save(user);

        return ResponseEntity.ok().build();
    }


}