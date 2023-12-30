package com.horizon.carpooling.services;

import com.horizon.carpooling.dao.UserRepository;
import com.horizon.carpooling.entities.User;
import com.horizon.carpooling.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


public class AbstractService {
    @Autowired
    private  AuthenticationService authenticationService;
    @Autowired
    private  UserRepository userDao;
    public User getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> user =   userDao.findByEmail(username);
        if(user.isPresent()){
            return user.get();
        }
        else {
            throw new UserNotFoundException();
        }

    }
}
