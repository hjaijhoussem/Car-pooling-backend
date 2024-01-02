package com.horizon.carpooling.services;

import com.horizon.carpooling.auth.AuthenticationRequest;
import com.horizon.carpooling.auth.AuthenticationResponse;
import com.horizon.carpooling.auth.RegisterRequest;
import com.horizon.carpooling.config.JwtService;
import com.horizon.carpooling.dao.UserRepository;
import com.horizon.carpooling.entities.enums.Role;
import com.horizon.carpooling.entities.User;
import com.horizon.carpooling.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        boolean userExist = userRepository.findByEmail(request.getEmail()).isPresent();
        if (userExist) throw new EmailAlreadyExistException();
        boolean cinExist = userRepository.findByCIN(request.getCIN()).isPresent();
        if (cinExist) throw new CinAlreadyInUseException();
        boolean phoneExsit = userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent();
        if (phoneExsit) throw new PhoneNumberAlreadyInUseException();
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .CIN(request.getCIN())
                .phoneNumber(request.getPhoneNumber())
                .isActive(true)
                .build();
        userRepository.save(user);
        var jwtToken =  jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new IncorrectPasswordException();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        var jwtToken =  jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}