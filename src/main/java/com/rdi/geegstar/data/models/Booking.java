package com.rdi.geegstar.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Entity
@Table(name = "bookings")
@NoArgsConstructor
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private User talent;
    @ManyToOne
    private User planner;
    @OneToOne(cascade = ALL)
    private EventDetail eventDetail;
    @OneToOne(cascade = ALL)
    private Calendar calendar;
    private boolean isAccepted;
}
