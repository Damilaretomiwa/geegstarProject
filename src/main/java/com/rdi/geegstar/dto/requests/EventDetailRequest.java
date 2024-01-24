package com.rdi.geegstar.dto.requests;

import com.rdi.geegstar.enums.EventType;
import com.rdi.geegstar.exceptions.WrongDateAndTimeFormat;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class EventDetailRequest {
    private String eventName;
    private String eventDateAndTime;
    private AddressRequest eventAddress;
    private EventType eventType;

    public void setEventDateAndTime(String eventDateAndTime) throws WrongDateAndTimeFormat {
        String pattern = "\\d{4}, \\d{2}, \\d{2}, \\d{2}, \\d{2}";
        boolean isNotMatchedPattern = !eventDateAndTime.matches(pattern);
        if(isNotMatchedPattern) throw new WrongDateAndTimeFormat(
                "Date and time must be in the format YYYY-MM-DD HH:mm. For example: 2023, 12, 24, 16, 30");
        this.eventDateAndTime = eventDateAndTime;
    }
}
