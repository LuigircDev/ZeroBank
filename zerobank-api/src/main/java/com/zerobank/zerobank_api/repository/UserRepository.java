package com.zerobank.zerobank_api.repository;

import com.zerobank.zerobank_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    // JpaRepository ya nos da gratis:
    // .save()
    // .findById()
    // .findAll()
    // .delete()
    // ¡No necesitamos escribir nada más aquí por ahora!
}
