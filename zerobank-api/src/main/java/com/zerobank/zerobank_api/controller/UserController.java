package com.zerobank.zerobank_api.controller;

import com.zerobank.zerobank_api.model.User;
import com.zerobank.zerobank_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint para que un usuario obtenga los detalles de SU PROPIA cuenta.
     * Este endpoint está protegido y requiere un token de autenticación válido.
     * @param id El ID del usuario cuya cuenta se solicita.
     * @param authentication Objeto inyectado por Spring Security que contiene los datos del usuario autenticado.
     * @return La cuenta del usuario si la validación es exitosa, o un error en caso contrario.
     */
    @GetMapping("/{id}/account")
    public ResponseEntity<?> getUserAccount(@PathVariable Long id, Authentication authentication) {
        // Del token, Spring Security nos da el "nombre" del usuario, que configuramos para que sea el email.
        String authenticatedUserEmail = authentication.getName();

        // Buscamos al usuario autenticado en la base de datos usando su email.
        Optional<User> userOptional = userRepository.findByEmail(authenticatedUserEmail);

        if (userOptional.isEmpty()) {
            // Este caso es una salvaguarda. Si el token es válido, el usuario debería existir.
            return new ResponseEntity<>("Usuario autenticado no encontrado.", HttpStatus.UNAUTHORIZED);
        }

        User authenticatedUser = userOptional.get();

        // --- ¡LA REGLA DE AUTORIZACIÓN CLAVE! ---
        // Comparamos si el ID del usuario dueño del token...
        // ...es el mismo que el ID que se está pidiendo en la URL.
        if (!authenticatedUser.getId().equals(id)) {
            // Si no coinciden, es un intento de ver datos ajenos. Lo prohibimos.
            return new ResponseEntity<>("Acceso prohibido. No puedes ver la cuenta de otro usuario.", HttpStatus.FORBIDDEN);
        }

        // Si la verificación es exitosa, todo está en orden. Devolvemos los datos de la cuenta.
        return ResponseEntity.ok(authenticatedUser.getAccount());
    }
}

