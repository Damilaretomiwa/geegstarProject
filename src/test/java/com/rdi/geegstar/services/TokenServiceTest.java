package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Token;
import com.rdi.geegstar.exceptions.EmailConfirmationFailedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Test
    public void testGenerateToken() {
        String userEmail = "dayokr@gmail.com";
        Token token= tokenService.generateToken(userEmail);
        assertNotNull(token);
    }

    @Test
    @Sql("/db/insert.sql")
    public void testConfirmEmail() throws EmailConfirmationFailedException {
        String userEmail = "john499@qianhost.com";
        String code = "4532";
        boolean confirmEmailResponse = tokenService.confirmEmail(userEmail, code);
        assertTrue(confirmEmailResponse);
    }
}
