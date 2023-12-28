package com.horizon.carpooling.controllers;

import com.horizon.carpooling.auth.AuthenticationRequest;
import com.horizon.carpooling.auth.AuthenticationResponse;
import com.horizon.carpooling.auth.RegisterRequest;
import com.horizon.carpooling.exception.IncorrectPasswordException;
import com.horizon.carpooling.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateRequest(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
