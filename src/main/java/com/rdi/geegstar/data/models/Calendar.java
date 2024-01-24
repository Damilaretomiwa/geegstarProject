package com.rdi.geegstar.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Entity(name = "Calendars")
@AllArgsConstructor
@NoArgsConstructor
public class Calendar {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private User talent;
    private Boolean isBooked;
    private LocalDateTime eventDateAndTime;
    private LocalDateTime dateCreated;

    @PrePersist
    public void setDateRegistered() {
        this.dateCreated = LocalDateTime.now();
    }

}
