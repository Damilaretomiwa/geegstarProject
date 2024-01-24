package com.rdi.geegstar.dto.requests;

import com.rdi.geegstar.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
}
