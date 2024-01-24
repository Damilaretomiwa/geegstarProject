package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.Token;
import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.data.repositories.UserRepository;
import com.rdi.geegstar.dto.requests.EmailRequest;
import com.rdi.geegstar.dto.requests.GetAllTalentsRequest;
import com.rdi.geegstar.dto.requests.Recipient;
import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.dto.response.GetAllTalentsResponse;
import com.rdi.geegstar.dto.response.GetUserResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.exceptions.EmailConfirmationFailedException;
import com.rdi.geegstar.exceptions.EmailIsTakenException;
import com.rdi.geegstar.exceptions.GeegStarException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.MailService;
import com.rdi.geegstar.services.TokenService;
import com.rdi.geegstar.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.regex.Pattern;

import static com.rdi.geegstar.enums.Role.TALENT;

@Service
@AllArgsConstructor
public class GeegStarUserService implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final MailService mailService;

    @Override
    public RegistrationResponse registerUser(RegistrationRequest registerRequest) {
        User user = modelMapper.map(registerRequest, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, RegistrationResponse.class);
    }

    @Override
    public Object requestEmailConfirmationCode(String userEmail) throws GeegStarException {
        boolean isEmailAvailable = isEmailAvailable(userEmail);
        Token generatedToken = tokenService.generateToken(userEmail);
        String tokenCode = generatedToken.getTokenCode();
        if(isEmailAvailable) emailConfirmationCodeTo(userEmail, tokenCode);
        return "Successful";
    }

    @Override
    public Boolean confirmEmail(String userEmail, String tokenCode) throws EmailConfirmationFailedException {
        return tokenService.confirmEmail(userEmail, tokenCode);
    }

    @Override
    public User findUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User was not found in our system"));
    }

    @Override
    public GetUserResponse getUserById(Long userId) throws UserNotFoundException {
        User user = findUserById(userId);
        return modelMapper.map(user, GetUserResponse.class);
    }

    @Override
    public List<GetAllTalentsResponse> getAllTalents(GetAllTalentsRequest getAllTalentRequest) {
        Pageable pageable = PageRequest.of(getAllTalentRequest.getPageNumber(), getAllTalentRequest.getPageSize());
        Page<User> talentPage = userRepository.findAllByRole(TALENT, pageable);
        List<User> talents = talentPage.getContent();
        return talents.stream()
                .map(user -> modelMapper.map(user, GetAllTalentsResponse.class))
                .toList();
    }

    private void emailConfirmationCodeTo(String userEmail, String tokenCode) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setSubject("Email confirmation on GeegStar service");
        emailRequest.setRecipients(List.of(new Recipient(userEmail, "Prospective client")));
        emailRequest.setHtmlContent(
                String.format(
                        "<p>Welcome to GeegStar services. \n Here is your confirmation code %s",
                        tokenCode
                ));
        mailService.sendMail(emailRequest);
    }

    private boolean isEmailAvailable(String userEmail) throws GeegStarException {
        String regexPattern = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        boolean isNotValidEmail = !Pattern.compile(regexPattern).matcher(userEmail).matches();
        if(isNotValidEmail) throw new GeegStarException(String.format("The email %s is not valid", userEmail));
        boolean isEmailTaken = userRepository.findByEmail(userEmail).isPresent();
        if (isEmailTaken) throw new EmailIsTakenException(String.format("The email %s is taken", userEmail));
        return true;
    }
}
