package com.rdi.geegstar.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingRequest {
    private EventDetailRequest eventDetailRequest;
    private Long plannerId;
    private Long talentId;
}
