package com.zerobank.zerobank_api.repository;

import com.zerobank.zerobank_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository ya nos da gratis:
    // .save()
    // .findById()
    // .findAll()
    // .delete()
    // ¡No necesitamos escribir nada más aquí por ahora!
}
