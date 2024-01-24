package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Token;
import com.rdi.geegstar.exceptions.EmailConfirmationFailedException;

public interface TokenService {

    Token generateToken(String userEmail);

    Boolean confirmEmail(String userEmail, String tokenCode) throws EmailConfirmationFailedException;
}
