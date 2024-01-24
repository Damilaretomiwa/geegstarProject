package com.rdi.geegstar.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBookingResponse {
    private Long id;
    private BookingResponsePlannerResponse plannerResponse;
    private BookingResponseTalentResponse talentResponse;
    private BookingResponseEventDetailResponse eventDetail;
    private BookingResponseCalenderResponse calendar;
    private boolean isAccepted;
}
