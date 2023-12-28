package com.horizon.carpooling.controllers;

import com.horizon.carpooling.dto.user.UserDetailDto;
import com.horizon.carpooling.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/users")
    public ResponseEntity<List<UserDetailDto>> getUsersList(){
        return ResponseEntity.ok(adminService.getUserList());
    }

    @PutMapping("/users/{userId}/dis-activate")
    public ResponseEntity<UserDetailDto> disactivateUserAccount(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(adminService.disactivate(userId));
    }

    @PutMapping("/users/{userId}/activate")
    public ResponseEntity<UserDetailDto> activateUserAccount(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(adminService.activate(userId));
    }
}
