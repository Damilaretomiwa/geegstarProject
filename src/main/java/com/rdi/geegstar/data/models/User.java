package com.rdi.geegstar.data.models;


import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.enums.TalentCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String displayName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String profilePicture;
    private TalentCategory category;
    private LocalDateTime dateRegistered;

    @PrePersist
    public void setDateRegistered() {
        this.dateRegistered = LocalDateTime.now();
    }
}
