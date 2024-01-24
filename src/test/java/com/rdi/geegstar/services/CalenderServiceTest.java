package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.dto.response.GetTalentCalendars;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.exceptions.WrongDateAndTimeFormat;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static com.rdi.geegstar.enums.Role.PLANNER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class CalenderServiceTest {

    @Autowired
    private CalendarService calenderService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;

    @Test
    public void testGetTalentCalenders() throws WrongDateAndTimeFormat, UserNotFoundException, BookingNotFoundException {
        RegistrationResponse talentRegistrationResponse = getRegistrationResponse(Role.TALENT);
        RegistrationResponse plannerRegistrationResponse = getRegistrationResponse(PLANNER);
        BookingRequest bookTalentRequest = new BookingRequest();
        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());
        BookingResponse firstBookingResponse = bookingService.bookTalent(bookTalentRequest);
        BookingResponse secondBookingResponse = bookingService.bookTalent(bookTalentRequest);
        AcceptBookingRequest acceptBookingRequest = new AcceptBookingRequest();
        acceptBookingRequest.setTalentId(talentRegistrationResponse.getId());
        acceptBookingRequest.setBookingId(firstBookingResponse.getBookingId());
        bookingService.acceptBooking(acceptBookingRequest);

        Long talentId = talentRegistrationResponse.getId();
        List<GetTalentCalendars> getTalentCalenderResponse =
                calenderService.getTalentCalendars(talentId);
        log.info("Calender :: {}", getTalentCalenderResponse);
        assertThat(getTalentCalenderResponse).isNotNull();
    }

    private RegistrationResponse getRegistrationResponse(Role talent) {
        RegistrationRequest registerRequest = new RegistrationRequest();
        registerRequest.setFirstName("Retnaa");
        registerRequest.setLastName("Dayok");
        registerRequest.setUsername("Darda");
        registerRequest.setEmail("dayokr@gmail.com");
        registerRequest.setPhoneNumber("07031005737");
        registerRequest.setPassword("password");
        registerRequest.setRole(talent);
        return userService.registerUser(registerRequest);
    }

    private EventDetailRequest getEventDetailRequest() throws WrongDateAndTimeFormat {
        EventDetailRequest eventDetailsRequest = new EventDetailRequest();
        eventDetailsRequest.setEventName("Darda's birthday party");
        eventDetailsRequest.setEventType(BIRTHDAY_PARTY);
        eventDetailsRequest.setEventDateAndTime("2023, 12, 04, 10, 30");
        AddressRequest addressRequest = getAddressRequest();
        eventDetailsRequest.setEventAddress(addressRequest);
        return eventDetailsRequest;
    }

    private AddressRequest getAddressRequest() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setCity("Lagos mainland");
        addressRequest.setCountry("Nigeria");
        addressRequest.setState("Lagos");
        addressRequest.setBuildingNumber(4634L);
        addressRequest.setStreet("Yaba");
        return addressRequest;
    }
}
