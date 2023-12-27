package com.horizon.carpooling.controllers;

import com.horizon.carpooling.dto.UserDto;
import com.horizon.carpooling.entities.User;
import com.horizon.carpooling.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsersList(){
        return ResponseEntity.ok(adminService.getUserList());
    }


}
