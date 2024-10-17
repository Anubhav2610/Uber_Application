package com.agyat.project.uber.uberApp.controllers;

import com.agyat.project.uber.uberApp.dto.SignupDto;
import com.agyat.project.uber.uberApp.dto.UserDto;
import com.agyat.project.uber.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignupDto signupDto){
        return authService.signup(signupDto);
    }
}
