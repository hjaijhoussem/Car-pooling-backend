package com.horizon.carpooling.controllers;

import com.horizon.carpooling.dao.UserRepository;
import com.horizon.carpooling.dto.user.UserDetailDto;
import com.horizon.carpooling.dto.user.UserUpdateRequestDto;
import com.horizon.carpooling.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    @GetMapping("/me")
    public ResponseEntity<UserDetailDto> getUserInfo(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.getUserInfo(userDetails));
    }

    @PutMapping("/me")
    public ResponseEntity<UserDetailDto> updateUserAccount(@AuthenticationPrincipal UserDetails userDetails
                                                            ,@RequestBody @Valid UserUpdateRequestDto updatedUser){
        return ResponseEntity.ok(userService.updateUserAccount(userDetails, updatedUser));
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteAccount(@AuthenticationPrincipal UserDetails userDetails){
        userService.deleteAccount(userDetails);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
