package com.zerobank.zerobank_api;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePasswordHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "password123";
        String hash = encoder.encode(password);

        System.out.println("Plain password: " + password);
        System.out.println("BCrypt hash: " + hash);
        System.out.println("Hash length: " + hash.length());

        // Verificar que el hash funciona
        boolean matches = encoder.matches(password, hash);
        System.out.println("Hash verification: " + matches);

        // Verificar el hash actual en la DB
        String dbHash = "$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5Y5MZfZQqH7EO";
        boolean dbMatches = encoder.matches(password, dbHash);
        System.out.println("DB hash matches 'password123': " + dbMatches);
    }
}
