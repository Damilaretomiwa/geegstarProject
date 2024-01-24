package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.Token;
import com.rdi.geegstar.data.repositories.TokenRepository;
import com.rdi.geegstar.exceptions.EmailConfirmationFailedException;
import com.rdi.geegstar.services.TokenService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GeegStarTokenService implements TokenService {

    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;

    @Override
    public Token generateToken(String userEmail) {
        String tokenCode = generateFourDigitTokenCode();
        Token token = new Token();
        token.setEmail(userEmail);
        token.setTokenCode(tokenCode);
        return tokenRepository.save(token);
    }

    @Override
    public Boolean confirmEmail(String userEmail, String tokenCode) throws EmailConfirmationFailedException {
        List<Token> tokens = tokenRepository.findAllByEmail(userEmail);
        for(Token token:tokens){
            boolean isSearchedToken = token.getTokenCode().equals(tokenCode);
            if(isSearchedToken) {
                token.setConfirmedAt(LocalDateTime.now());
                tokenRepository.save(token);
                return true;
            };
        }
        throw new EmailConfirmationFailedException("The code you provided is incorrect");
    }

    private String generateFourDigitTokenCode() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) builder.append(secureRandom.nextInt(10));
        return builder.toString();
    }
}
