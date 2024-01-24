package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetAllTalentsResponse {
    private String displayName;
    private Long id;
    private String profilePicture;
    private String bio;
}
