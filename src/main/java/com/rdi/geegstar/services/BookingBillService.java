package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.BookingBill;
import com.rdi.geegstar.dto.requests.BookingBillRequest;
import com.rdi.geegstar.dto.requests.PayBookingBillRequest;
import com.rdi.geegstar.dto.response.BookingBillPaymentResponse;
import com.rdi.geegstar.dto.response.BookingBillResponse;
import com.rdi.geegstar.exceptions.BookingBillNotFoundException;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;

public interface BookingBillService {
    BookingBillResponse createBookingBill(BookingBillRequest bookingBillRequest)
            throws UserNotFoundException, BookingNotFoundException;

    BookingBill findBookingBillById(Long bookingBillId)
            throws BookingBillNotFoundException;

    BookingBillPaymentResponse payBookingBill(PayBookingBillRequest payBookingBillRequest)
            throws BookingBillNotFoundException, UserNotFoundException;
}
