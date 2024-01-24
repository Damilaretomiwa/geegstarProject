package com.rdi.geegstar.services;


import com.rdi.geegstar.dto.requests.EmailRequest;
import com.rdi.geegstar.dto.requests.Recipient;
import com.rdi.geegstar.dto.response.EmailResponse;
import com.rdi.geegstar.exceptions.GeegStarException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendEmail() {
        EmailRequest emailRequest = new EmailRequest();


        Recipient recipient = new Recipient();
        recipient.setEmail("max_ret@yahoo.com");
        recipient.setName("Ret Max");
        Recipient recipient1 = new Recipient();
        recipient1.setEmail("xisar98726@cindalle.com");
        recipient1.setName("Darda Maxwell");
        List<Recipient> recipients = List.of(
                recipient,
                recipient1
        );

        emailRequest.setRecipients(recipients);
        emailRequest.setHtmlContent("<p>I am testing my application</p>");
        emailRequest.setSubject("Testing my springboot app...");

        EmailResponse emailResponse = mailService.sendMail(emailRequest);
        assertNotNull(emailResponse);
        assertNotNull(emailResponse.getMessageId());
        assertNotNull(emailResponse.getCode());
        assertEquals(201, emailResponse.getCode());
    }

}
