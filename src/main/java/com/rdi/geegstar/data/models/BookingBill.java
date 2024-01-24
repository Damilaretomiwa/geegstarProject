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
public class BookingBill {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    private Booking booking;
    @OneToOne
    private User planner;
    @OneToOne
    private User talent;
    private String text;
    private BigDecimal amount;
    @OneToOne
    private Payment payment;
    private LocalDateTime dateTimeCreated;

    @PrePersist
    public void setDateTimeCreated() {
        dateTimeCreated = LocalDateTime.now();
    }

}
