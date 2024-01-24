package com.rdi.geegstar.services;


import com.rdi.geegstar.dto.requests.EmailRequest;
import com.rdi.geegstar.dto.response.EmailResponse;
import com.rdi.geegstar.exceptions.GeegStarException;

public interface MailService {

    EmailResponse sendMail(EmailRequest emailRequest);
}
