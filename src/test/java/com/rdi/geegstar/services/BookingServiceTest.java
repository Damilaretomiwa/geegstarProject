package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Booking;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.*;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.exceptions.WrongDateAndTimeFormat;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static com.rdi.geegstar.enums.Role.PLANNER;
import static com.rdi.geegstar.enums.Role.TALENT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    @Test
    public void testBookTalent() throws UserNotFoundException, WrongDateAndTimeFormat {
        BookingRequest bookTalentRequest = getBookingRequest();

        BookingResponse bookTalentResponse = bookingService.bookTalent(bookTalentRequest);

        assertThat(bookTalentResponse).isNotNull();
    }

    @Test
    public void testAcceptBooking() throws UserNotFoundException, BookingNotFoundException, WrongDateAndTimeFormat {
        BookingRequest bookTalentRequest = getBookingRequest();

        BookingResponse bookTalentResponse = bookingService.bookTalent(bookTalentRequest);

        AcceptBookingRequest acceptBookingRequest = new AcceptBookingRequest();
        acceptBookingRequest.setBookingId(bookTalentResponse.getBookingId());
        acceptBookingRequest.setTalentId(bookTalentRequest.getTalentId());

        AcceptBookingResponse acceptBookingResponse =
                bookingService.acceptBooking(acceptBookingRequest);

        assertThat(acceptBookingResponse).isNotNull();
    }

    @Test
    public void testDeclineBooking() throws UserNotFoundException, BookingNotFoundException, WrongDateAndTimeFormat {
        BookingRequest bookTalentRequest = getBookingRequest();

        BookingResponse bookTalentResponse = bookingService.bookTalent(bookTalentRequest);


        DeclineBookingResponse declineBookingResponse =
                bookingService.declineBooking(bookTalentResponse.getBookingId());
        assertThat(declineBookingResponse).isNotNull();
    }

    @Test
    public void testFindBookingById() throws WrongDateAndTimeFormat, UserNotFoundException, BookingNotFoundException {
        BookingRequest bookTalentRequest = getBookingRequest();
        BookingResponse bookTalentResponse = bookingService.bookTalent(bookTalentRequest);

        Booking foundBooking = bookingService.findBookingById(bookTalentResponse.getBookingId());

        assertThat(foundBooking).isNotNull();
    }

    @Test
    public void testGetUserBookings() throws WrongDateAndTimeFormat, UserNotFoundException {
        RegistrationResponse talentRegistrationResponse = getRegistrationResponse(Role.TALENT);
        RegistrationResponse talentRegistrationResponse2 = getRegistrationResponse(Role.TALENT);
        RegistrationResponse plannerRegistrationResponse = getRegistrationResponse(PLANNER);
        EventDetailRequest eventDetailsRequest = getEventDetailRequest();

        BookingRequest bookTalentRequest = new BookingRequest();
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());

        BookingRequest bookTalentRequest2 = new BookingRequest();
        bookTalentRequest2.setTalentId(talentRegistrationResponse2.getId());
        bookTalentRequest2.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest2.setPlannerId(plannerRegistrationResponse.getId());

        bookingService.bookTalent(bookTalentRequest);
        bookingService.bookTalent(bookTalentRequest2);

        GetUserBookingsRequest getUserBookingsRequest = new GetUserBookingsRequest();
        int pageNumber = 1;
        int numberOfBookingsToPage = 1;
        getUserBookingsRequest.setUserId(talentRegistrationResponse2.getId());
        getUserBookingsRequest.setPageNumber(pageNumber);
        getUserBookingsRequest.setPageSize(numberOfBookingsToPage);
        getUserBookingsRequest.setUserRole(TALENT);

        List<UserBookingResponse> userBookingsResponse = bookingService.getUserBookings(getUserBookingsRequest);
//        log.info("bookings:: {}", plannerBookingResponse);
//        System.out.println(userBookingsResponse);
        Assertions.assertThat(userBookingsResponse).hasSize(numberOfBookingsToPage);
    }

    private BookingRequest getBookingRequest() throws WrongDateAndTimeFormat {
        RegistrationResponse talentRegistrationResponse = getRegistrationResponse(Role.TALENT);

        RegistrationResponse plannerRegistrationResponse = getRegistrationResponse(PLANNER);

        BookingRequest bookTalentRequest = new BookingRequest();
        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());
        return bookTalentRequest;
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
        RegistrationResponse talentRegistrationResponse = userService.registerUser(registerRequest);
        return talentRegistrationResponse;
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
