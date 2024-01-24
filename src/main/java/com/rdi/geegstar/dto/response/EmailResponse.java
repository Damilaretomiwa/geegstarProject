package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailResponse {
    private String messageId;
    private Integer code;
}
