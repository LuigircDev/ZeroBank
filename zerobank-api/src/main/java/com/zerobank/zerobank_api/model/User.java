package com.zerobank.zerobank_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_user") // Mejor no llamar "User" a la tabla (es palabra reservada)
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @OneToOne(cascade = CascadeType.ALL) // Si borro un User, se borra su Account
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public User(String name, String email, Account account) {
        this.name = name;
        this.email = email;
        this.account = account;
    }
}