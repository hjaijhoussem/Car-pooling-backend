package com.horizon.carpooling.services;

import com.horizon.carpooling.dao.RideRepository;
import com.horizon.carpooling.dao.RideRequestRepository;
import com.horizon.carpooling.dao.UserRepository;
import com.horizon.carpooling.dto.user.UserDetailDto;
import com.horizon.carpooling.dto.user.UserUpdateRequestDto;
import com.horizon.carpooling.entities.User;
import com.horizon.carpooling.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userDao;
    private final RideRequestRepository rideRequestDao;
    private final RideRepository rideDao;
    private final ModelMapper mapper;
    public UserDetailDto getUserInfo(UserDetails userDetails) {
        User authenticatedUser = userDao.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        return mapper.map(authenticatedUser, UserDetailDto.class);
    }

    public UserDetailDto updateUserAccount(UserDetails userDetails, UserUpdateRequestDto updatedUser) {
        User authenticatedUser = userDao.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        authenticatedUser.setEmail(updatedUser.getEmail());
        authenticatedUser.setFirstname(updatedUser.getFirstname());
        authenticatedUser.setLastname(updatedUser.getLastname());
        authenticatedUser.setPhoneNumber(updatedUser.getPhoneNumber());
        return mapper.map(userDao.save(authenticatedUser), UserDetailDto.class);
    }

    public void deleteAccount(UserDetails userDetails) {
        User authenticatedUser = userDao.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        rideRequestDao.deleteByUserEmail(authenticatedUser.getEmail());
        rideDao.deleteByUserEmail(authenticatedUser.getEmail());
        userDao.deleteById(authenticatedUser.getId());
    }
}
