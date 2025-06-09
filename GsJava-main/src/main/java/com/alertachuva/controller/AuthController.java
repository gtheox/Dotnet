package com.alertachuva.controller;

import com.alertachuva.model.Usuario;
import com.alertachuva.security.JwtUtil;
import com.alertachuva.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(loginRequest.getEmail());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getSenha().equals(loginRequest.getSenha())) {
                String token = jwtUtil.generateToken(usuario.getEmail());
                return ResponseEntity.ok(new LoginResponse(token));
            }
        }

        return ResponseEntity.status(401).body("Email ou senha inválidos");
}




    @Data
    public static class LoginRequest {
        private String email;
        private String senha;
    }

    @Data
    public static class LoginResponse {
        private final String token;
    }
}
