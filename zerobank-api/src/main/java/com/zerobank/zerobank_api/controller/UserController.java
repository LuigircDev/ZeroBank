package com.zerobank.zerobank_api.controller;

import com.zerobank.zerobank_api.model.Account;
import com.zerobank.zerobank_api.model.User;
import com.zerobank.zerobank_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users") // La URL base para este controlador
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // NOTA: Los métodos para crear y listar todos los usuarios han sido eliminados.
    // - La creación de usuarios (registro) debe ser manejada por un AuthController público.
    // - Listar todos los usuarios es una función de administrador y debe ser protegida por roles.

    /**
     * Endpoint para que un usuario obtenga los detalles de SU PROPIA cuenta.
     * Este endpoint está protegido y requiere un token de autenticación válido.
     */
    @GetMapping("/{id}/account")
    public ResponseEntity<?> getUserAccount(@PathVariable Long id, Authentication authentication) {
        // Del token de autenticación, obtenemos el nombre de usuario (en nuestro caso, el email).
        String authenticatedUserEmail = authentication.getName();

        // Buscamos al usuario autenticado en la base de datos.
        // NOTA: Debes añadir el método 'findByEmail' a tu interface UserRepository.
        Optional<User> userOptional = userRepository.findByEmail(authenticatedUserEmail);

        if (userOptional.isEmpty()) {
            // Este caso es poco probable si el token es válido, pero es una buena salvaguarda.
            return new ResponseEntity<>("Usuario autenticado no encontrado.", HttpStatus.UNAUTHORIZED);
        }

        User authenticatedUser = userOptional.get();

        // ¡LA REGLA DE SEGURIDAD MÁS IMPORTANTE!
        // Verificamos si el ID del usuario autenticado es el mismo que el ID solicitado en la URL.
        if (!authenticatedUser.getId().equals(id)) {
            // Si no coinciden, denegamos el acceso con un error 403 Forbidden.
            return new ResponseEntity<>("Acceso prohibido. No puedes ver la cuenta de otro usuario.", HttpStatus.FORBIDDEN);
        }

        // Si la verificación es exitosa, devolvemos la cuenta del usuario.
        return ResponseEntity.ok(authenticatedUser.getAccount());
    }
}

