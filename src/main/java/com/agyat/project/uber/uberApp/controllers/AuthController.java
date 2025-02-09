package com.agyat.project.uber.uberApp.controllers;

import com.agyat.project.uber.uberApp.dto.DriverDto;
import com.agyat.project.uber.uberApp.dto.OnboardDriverDto;
import com.agyat.project.uber.uberApp.dto.SignupDto;
import com.agyat.project.uber.uberApp.dto.UserDto;
import com.agyat.project.uber.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupDto signupDto){
        return new ResponseEntity<>(authService.signup(signupDto) , HttpStatus.CREATED);
    }

    @PostMapping("/onBoardNewDriver/{userId}")
    public ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId ,
                                                      @RequestBody OnboardDriverDto onboardDriverDto){
        return new ResponseEntity<>(authService.onBoardNewDriver(userId ,
                onboardDriverDto.getVehicleId()) ,  HttpStatus.CREATED);
    }


}
