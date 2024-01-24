package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.dto.requests.GetAllTalentsRequest;
import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.dto.response.GetAllTalentsResponse;
import com.rdi.geegstar.dto.response.GetUserResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.exceptions.EmailConfirmationFailedException;
import com.rdi.geegstar.exceptions.GeegStarException;
import com.rdi.geegstar.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {
    RegistrationResponse registerUser(RegistrationRequest registerRequest);

    Object requestEmailConfirmationCode(String userEmail) throws GeegStarException;

    Boolean confirmEmail(String userEmail, String code) throws EmailConfirmationFailedException;

    User findUserById(Long userId) throws UserNotFoundException;

    GetUserResponse getUserById(Long userId) throws UserNotFoundException;

    List<GetAllTalentsResponse> getAllTalents(GetAllTalentsRequest getAllTalentsRequest);
}
