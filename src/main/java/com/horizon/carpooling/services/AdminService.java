package com.horizon.carpooling.services;

import com.horizon.carpooling.dao.UserRepository;
import com.horizon.carpooling.dto.UserDto;
import com.horizon.carpooling.entities.User;
import com.horizon.carpooling.entities.enums.Role;
import com.horizon.carpooling.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userDao;
    private final ModelMapper mapper;


    public List<UserDto> getUserList() {
        return userDao.findAll().stream()
                .filter(user -> !user.getRole().equals(Role.ADMIN)) // Assuming Role.ADMIN is an enum value
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto disactivate(Integer userId) {
        User user = userDao.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setActive(false);
        User updatedUser = userDao.save(user);
        return mapper.map(updatedUser, UserDto.class);
    }

    public UserDto activate(Integer userId) {
        User user = userDao.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setActive(true);
        User updatedUser = userDao.save(user);
        return mapper.map(updatedUser, UserDto.class);
    }
}
