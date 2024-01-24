package com.rdi.geegstar.dto.requests;

import com.rdi.geegstar.enums.Role;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class GetUserBookingsRequest {
    private Long userId;
    private int pageSize;
    private int pageNumber;
    private Role userRole;
}
