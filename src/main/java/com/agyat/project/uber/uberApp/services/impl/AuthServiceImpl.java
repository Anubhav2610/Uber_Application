package com.agyat.project.uber.uberApp.services.impl;

import com.agyat.project.uber.uberApp.dto.*;
import com.agyat.project.uber.uberApp.entities.Driver;
import com.agyat.project.uber.uberApp.entities.User;
import com.agyat.project.uber.uberApp.entities.enums.Role;
import com.agyat.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.agyat.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.agyat.project.uber.uberApp.repositories.UserRepository;
import com.agyat.project.uber.uberApp.security.JWTService;
import com.agyat.project.uber.uberApp.services.AuthService;
import com.agyat.project.uber.uberApp.services.DriverService;
import com.agyat.project.uber.uberApp.services.RiderService;
import com.agyat.project.uber.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.agyat.project.uber.uberApp.entities.enums.Role.DRIVER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper ;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public String[] login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email , password)
        );
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken , refreshToken};
    }

    @Override
    @Transactional
    public UserDto signup(SignupDto signupDto) {
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if(user != null){
            throw new RuntimeConflictException("Cannot signup , user already exist with email : "+ signupDto.getEmail());
        }
        User mappedUser = modelMapper.map(signupDto , User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);

        //Create user related entities
        riderService.createNewRider(savedUser);

        walletService.createNewWallet(savedUser);
        return modelMapper.map(savedUser , UserDto.class);
    }

    @Override
    public DriverDto onBoardNewDriver(Long userId , String vehicleId ) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not Found with id "+ userId));

        if(user.getRoles().contains(DRIVER)){
            throw new RuntimeConflictException("Can't Onboard , user with id "+ userId +" Is already a Driver");
        }

        Driver createDriver = Driver
                .builder()
                .vehicleId(vehicleId)
                .available(true)
                .rating(0.0)
                .user(user)
                .build();

        user.getRoles().add(DRIVER);
        userRepository.save(user);

        Driver savedDriver = driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver , DriverDto.class);


    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+userId)
                );

        return jwtService.generateAccessToken(user);


    }
}
