package com.rdi.geegstar.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.exceptions.WrongDateAndTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testBookTalent() throws WrongDateAndTimeFormat, UnsupportedEncodingException, JsonProcessingException {
        Long talentId = registerUser(Role.TALENT);
        Long plannerId = registerUser(Role.PLANNER);
        ObjectMapper mapper = new ObjectMapper();
        BookingRequest bookingRequest = getBookingRequest(plannerId, talentId);
        try {
            mockMvc.perform(
                            post("/api/v1/booking")
                                    .content(mapper.writeValueAsString(bookingRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testAcceptBooking() throws WrongDateAndTimeFormat, UnsupportedEncodingException, JsonProcessingException {
        Long talentId = registerUser(Role.TALENT);
        Long plannerId = registerUser(Role.PLANNER);
        ObjectMapper mapper = new ObjectMapper();
        BookingRequest bookingRequest = getBookingRequest(plannerId, talentId);
        MvcResult responseOfBooking = null;
        try {
            responseOfBooking = mockMvc.perform(
                            post("/api/v1/booking")
                                    .content(mapper.writeValueAsString(bookingRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        String content = responseOfBooking.getResponse().getContentAsString();
        BookingResponse bookingResponse = mapper.readValue(content, BookingResponse.class);

        Long bookingId = bookingResponse.getBookingId();
        AcceptBookingRequest acceptBookingRequest = new AcceptBookingRequest();
        acceptBookingRequest.setBookingId(bookingId);
        acceptBookingRequest.setTalentId(talentId);
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.patch("/api/v1/booking/accept")
                                    .content(mapper.writeValueAsString(acceptBookingRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testDeclineBooking() throws WrongDateAndTimeFormat, UnsupportedEncodingException, JsonProcessingException {
        Long talentId = registerUser(Role.TALENT);
        Long plannerId = registerUser(Role.PLANNER);
        ObjectMapper mapper = new ObjectMapper();
        BookingRequest bookingRequest = getBookingRequest(plannerId, talentId);
        MvcResult responseOfBooking = null;
        try {
            responseOfBooking = mockMvc.perform(
                            post("/api/v1/booking")
                                    .content(mapper.writeValueAsString(bookingRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        String content = responseOfBooking.getResponse().getContentAsString();
        BookingResponse bookingResponse = mapper.readValue(content, BookingResponse.class);
        Long bookingId = bookingResponse.getBookingId();

        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.patch("/api/v1/booking/decline/"+ bookingId)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static BookingRequest getBookingRequest(Long plannerId, Long talentId) throws WrongDateAndTimeFormat {
        EventDetailRequest eventDetailRequest =
                getEventDetailRequest();
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setPlannerId(plannerId);
        bookingRequest.setTalentId(talentId);
        bookingRequest.setEventDetailRequest(eventDetailRequest);
        return bookingRequest;
    }


    public Long registerUser(Role userRole) throws UnsupportedEncodingException, JsonProcessingException {
        String URL = "/api/v1/user";
        ObjectMapper mapper = new ObjectMapper();
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Retnaa");
        registrationRequest.setLastName("Dayok");
        registrationRequest.setUsername("Darda");
        registrationRequest.setEmail("dayokr@gmail.com");
        registrationRequest.setPhoneNumber("07031005737");
        registrationRequest.setPassword("password");
        registrationRequest.setRole(userRole);
        MvcResult userRegistrationResponse = null;
        try {
            userRegistrationResponse = mockMvc.perform(
                            post(URL)
                                    .content(mapper.writeValueAsString(registrationRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        String userResponse = userRegistrationResponse.getResponse().getContentAsString();
        RegistrationResponse registrationResponse = mapper.readValue(userResponse, RegistrationResponse.class);
        return registrationResponse.getId();
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
