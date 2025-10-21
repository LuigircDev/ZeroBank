package com.zerobank.zerobank_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Le dice a JPA que esto es una tabla de BD
@Data   // Lombok: crea getters, setters, toString(), etc.
@NoArgsConstructor // Lombok: crea un constructor vacío
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usamos BigDecimal para dinero, ¡nunca double o float!
    private java.math.BigDecimal balance;

    public Account(java.math.BigDecimal initialBalance) {
        this.balance = initialBalance;
    }
}
