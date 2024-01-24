package com.rdi.geegstar.data.models;

import com.rdi.geegstar.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Entity
@Table(name = "events_details")
@AllArgsConstructor
@NoArgsConstructor
public class EventDetail {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String eventName;
    private LocalDateTime eventDateAndTime;
    @OneToOne(cascade = CascadeType.ALL)
    private Address eventAddress;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
}
