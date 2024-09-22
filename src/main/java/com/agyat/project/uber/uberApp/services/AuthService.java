package com.agyat.project.uber.uberApp.services;

import com.agyat.project.uber.uberApp.dto.DriverDto;
import com.agyat.project.uber.uberApp.dto.SignupDto;
import com.agyat.project.uber.uberApp.dto.UserDto;

public interface AuthService {
    String login(String email , String  password);
    UserDto signup(SignupDto signupDto);
    DriverDto OnboardNewDriver(Long userId);
}
