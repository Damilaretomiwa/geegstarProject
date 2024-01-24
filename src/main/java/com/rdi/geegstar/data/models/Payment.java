package com.rdi.geegstar.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
public class Payment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    private User sender;
    @OneToOne
    private User receiver;
    private BigDecimal amount;
    private LocalDateTime dateCreated;

    @PrePersist
    private void setDateCreated() {
        dateCreated = LocalDateTime.now();
    }
}
