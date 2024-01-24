package com.rdi.geegstar.dto.response;

import com.rdi.geegstar.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetUserResponse {
    private String displayName;
    private String firstName;
    private String LastName;
    private Role Role;
    private Long id;
}
