package com.zerobank.zerobank_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    // --- IMPORTANTE ---
    // Aquí es donde implementarás la lógica para el login y el registro.
    // Necesitarás inyectar el AuthenticationManager de Spring Security y tu
    // servicio/utilidad de JWT para generar el token.

    // Ejemplo de cómo se vería el endpoint de login (necesita implementación)
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(/*@RequestBody LoginRequest loginRequest*/) {
        // 1. Autenticar al usuario con AuthenticationManager
        // 2. Si es exitoso, generar un token JWT
        // 3. Devolver el token en la respuesta
        // Por ahora, devolvemos un placeholder:
        return ResponseEntity.ok("Login endpoint - A IMPLEMENTAR");
    }

    /**
     * Endpoint de "health check" para que las pruebas de QA puedan verificar
     * si la API está viva antes de intentar obtener un token.
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API is up and running");
    }
}
