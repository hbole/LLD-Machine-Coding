package com.bookmyshow.controllers;

import com.bookmyshow.dto.ResponseStatus;
import com.bookmyshow.dto.SignUpRequestDTO;
import com.bookmyshow.dto.SignUpResponseDTO;
import com.bookmyshow.models.User;
import com.bookmyshow.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequest) {
        SignUpResponseDTO signUpResponse = new SignUpResponseDTO();

        try {
            User user = userService.signUp(
                    signUpRequest.getEmail(),
                    signUpRequest.getPassword()
            );

            signUpResponse.setUserId(user.getId());
            signUpResponse.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            signUpResponse.setResponseStatus(ResponseStatus.FAILURE);
        }

        return signUpResponse;
    }
}
