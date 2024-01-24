package com.rdi.geegstar.services;

import com.rdi.geegstar.config.MailConfig;
import com.rdi.geegstar.dto.requests.EmailRequest;
import com.rdi.geegstar.dto.response.EmailResponse;
import com.rdi.geegstar.exceptions.EmailIsTakenException;
import com.rdi.geegstar.exceptions.GeegStarException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.regex.Pattern;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class BrevoMailService implements MailService{

    private final MailConfig mailConfig;

    @Override
    public EmailResponse sendMail(EmailRequest emailRequest) {
        final String URL = mailConfig.getBrevoMailUrl();
        final String MY_API_KEY = mailConfig.getMailApiKey();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        httpHeaders.set("accept", APPLICATION_JSON_VALUE);
        httpHeaders.set("api-key", MY_API_KEY);
        HttpEntity<EmailRequest> requestEntity =
                new RequestEntity<>(emailRequest, httpHeaders, POST, URI.create(""));
        ResponseEntity<EmailResponse> response =
                restTemplate.postForEntity(URL, requestEntity, EmailResponse.class);
        var emailResponse = response.getBody();
        emailResponse.setCode(response.getStatusCode().value());

        return emailResponse;
    }



}
