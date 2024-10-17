package com.agyat.project.uber.uberApp.services.impl;

import com.agyat.project.uber.uberApp.dto.DriverDto;
import com.agyat.project.uber.uberApp.dto.SignupDto;
import com.agyat.project.uber.uberApp.dto.UserDto;
import com.agyat.project.uber.uberApp.entities.Rider;
import com.agyat.project.uber.uberApp.entities.User;
import com.agyat.project.uber.uberApp.entities.enums.Role;
import com.agyat.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.agyat.project.uber.uberApp.repositories.RiderRepository;
import com.agyat.project.uber.uberApp.repositories.UserRepository;
import com.agyat.project.uber.uberApp.services.AuthService;
import com.agyat.project.uber.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper ;
    private final UserRepository userRepository;
    private final RiderService riderService;

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public UserDto signup(SignupDto signupDto) {
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if(user != null){
            throw new RuntimeConflictException("Cannot signup , user already exist with email : "+ signupDto.getEmail());
        }
        User mappedUser = modelMapper.map(signupDto , User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(mappedUser);

        //Create user related entities
        riderService.createNewRider(savedUser);

        //TODO Add Wallet Related Service Here
        return modelMapper.map(savedUser , UserDto.class);
    }

    @Override
    public DriverDto OnboardNewDriver(Long userId) {
        return null;
    }
}
