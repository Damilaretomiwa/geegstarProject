package com.rdi.geegstar.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetAllTalentsRequest {
    private int pageNumber;
    private int pageSize;
}
