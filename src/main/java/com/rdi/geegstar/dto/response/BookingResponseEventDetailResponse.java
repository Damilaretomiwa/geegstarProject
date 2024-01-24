package com.rdi.geegstar.dto.response;

import com.rdi.geegstar.data.models.Address;
import com.rdi.geegstar.enums.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Setter
@Getter
public class BookingResponseEventDetailResponse {
    private String eventName;
    private LocalDateTime eventDateAndTime;
    private BookingResponseAddressResponse eventAddress;
    private EventType eventType;
}
