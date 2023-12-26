package com.horizon.carpooling.services;

import com.horizon.carpooling.dao.UserRepository;
import com.horizon.carpooling.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userDao;

    public List<UserDto> getUserList() {
        return null;
    }
}
