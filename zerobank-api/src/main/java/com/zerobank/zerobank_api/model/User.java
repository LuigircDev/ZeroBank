package com.zerobank.zerobank_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "app_user")
@Data
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true) // El email debe ser único
    private String email;

    private String password; // Campo para la contraseña encriptada

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public User(String name, String email, String password, Account account) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.account = account;
    }

    // --- Métodos de la interfaz UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Para este proyecto simple, no manejamos roles. Devolvemos una lista vacía.
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        // Usaremos el email como el "username" para la autenticación.
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
