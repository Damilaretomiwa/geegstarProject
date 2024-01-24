package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.BookingBill;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.BookingBillPaymentResponse;
import com.rdi.geegstar.dto.response.BookingBillResponse;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.exceptions.BookingBillNotFoundException;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.exceptions.WrongDateAndTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BookingBillServiceTest {

    @Autowired
    private BookingBillService bookingBillService;
    @Autowired
    private BookingService bookTalentService;
    @Autowired
    private UserService userService;

    @Test
    public void testCreateBookingBill()
            throws UserNotFoundException, BookingNotFoundException, WrongDateAndTimeFormat {
        BookingRequest bookTalentRequest = getBookingRequest();
        BookingResponse bookTalentResponse = bookTalentService.bookTalent(bookTalentRequest);
        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal cost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(cost);
        bookingBillRequest.setBookingId(bookTalentResponse.getBookingId());
        bookingBillRequest.setText("The cost covers for all expenses");
        RegistrationResponse talentRegistrationResponse = getUserRegistrationResponse(Role.TALENT);
        RegistrationResponse plannerRegistrationResponse = getUserRegistrationResponse(Role.PLANNER);
        bookingBillRequest.setPlannerId(talentRegistrationResponse.getId());
        bookingBillRequest.setTalentId(plannerRegistrationResponse.getId());

        BookingBillResponse bookingBillResponse = bookingBillService.createBookingBill(bookingBillRequest);
        assertThat(bookingBillResponse).isNotNull();
    }

    @Test
    public void testFindBookingBillById()
            throws WrongDateAndTimeFormat, UserNotFoundException,
            BookingNotFoundException, BookingBillNotFoundException {
        BookingRequest bookTalentRequest = getBookingRequest();
        BookingResponse bookTalentResponse = bookTalentService.bookTalent(bookTalentRequest);
        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal cost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(cost);
        bookingBillRequest.setBookingId(bookTalentResponse.getBookingId());
        bookingBillRequest.setText("The cost covers for all expenses");
        RegistrationResponse talentRegistrationResponse = getUserRegistrationResponse(Role.TALENT);
        RegistrationResponse plannerRegistrationResponse = getUserRegistrationResponse(Role.PLANNER);
        bookingBillRequest.setPlannerId(talentRegistrationResponse.getId());
        bookingBillRequest.setTalentId(plannerRegistrationResponse.getId());
        BookingBillResponse bookingBillResponse = bookingBillService.createBookingBill(bookingBillRequest);

        BookingBill foundBookingBill = bookingBillService.findBookingBillById(bookingBillResponse.getBookingBillId());

        assertThat(foundBookingBill).isNotNull();
    }

    @Test
    public void testPayBookingBill()
            throws WrongDateAndTimeFormat, UserNotFoundException,
            BookingNotFoundException, BookingBillNotFoundException {
        BookingRequest bookTalentRequest = getBookingRequest();
        BookingResponse bookTalentResponse = bookTalentService.bookTalent(bookTalentRequest);
        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal cost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(cost);
        bookingBillRequest.setBookingId(bookTalentResponse.getBookingId());
        bookingBillRequest.setText("The cost covers for all expenses");
        RegistrationResponse talentRegistrationResponse = getUserRegistrationResponse(Role.TALENT);
        RegistrationResponse plannerRegistrationResponse = getUserRegistrationResponse(Role.PLANNER);
        bookingBillRequest.setPlannerId(talentRegistrationResponse.getId());
        bookingBillRequest.setTalentId(plannerRegistrationResponse.getId());
        BookingBillResponse bookingBillResponse = bookingBillService.createBookingBill(bookingBillRequest);

        PayBookingBillRequest payBookingBillRequest = new PayBookingBillRequest();
        payBookingBillRequest.setBookingBillId(bookingBillResponse.getBookingBillId());
        payBookingBillRequest.setSenderId(plannerRegistrationResponse.getId());
        payBookingBillRequest.setReceiverId(talentRegistrationResponse.getId());

        BookingBillPaymentResponse bookingBillPaymentResponse =
                bookingBillService.payBookingBill(payBookingBillRequest);

        assertThat(bookingBillPaymentResponse).isNotNull();
    }

    private BookingRequest getBookingRequest() throws WrongDateAndTimeFormat {
        RegistrationResponse talentRegistrationResponse = getUserRegistrationResponse(Role.TALENT);

        RegistrationResponse plannerRegistrationResponse = getUserRegistrationResponse(Role.PLANNER);

        BookingRequest bookTalentRequest = new BookingRequest();
        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());
        return bookTalentRequest;
    }

    private RegistrationResponse getUserRegistrationResponse(Role role) {
        RegistrationRequest talentRegisterRequest = new RegistrationRequest();
        talentRegisterRequest.setFirstName("Retnaa");
        talentRegisterRequest.setLastName("Dayok");
        talentRegisterRequest.setUsername("Darda");
        talentRegisterRequest.setEmail("dayokr@gmail.com");
        talentRegisterRequest.setPhoneNumber("07031005737");
        talentRegisterRequest.setPassword("password");
        talentRegisterRequest.setRole(role);
        return userService.registerUser(talentRegisterRequest);
    }

    private static EventDetailRequest getEventDetailRequest() throws WrongDateAndTimeFormat {
        EventDetailRequest eventDetailsRequest = new EventDetailRequest();
        eventDetailsRequest.setEventName("Darda's birthday party");
        eventDetailsRequest.setEventType(BIRTHDAY_PARTY);
        eventDetailsRequest.setEventDateAndTime("2023, 12, 04, 10, 30");
        AddressRequest addressRequest = getAddressRequest();
        eventDetailsRequest.setEventAddress(addressRequest);
        return eventDetailsRequest;
    }

    private static AddressRequest getAddressRequest() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setCity("Lagos mainland");
        addressRequest.setCountry("Nigeria");
        addressRequest.setState("Lagos");
        addressRequest.setBuildingNumber(4634L);
        addressRequest.setStreet("Yaba");
        return addressRequest;
    }
}
