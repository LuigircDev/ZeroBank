package com.zerobank.zerobank_api.service;

import com.zerobank.zerobank_api.model.User;
import com.zerobank.zerobank_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // AGREGAR LOGS PARA DEBUG
        System.out.println("=== Usuario encontrado: " + user.getEmail());
        System.out.println("=== Password del usuario: " + user.getPassword());

        return user; // Debe devolver el User directamente
    }
}
