package com.horizon.carpooling.services;

import com.horizon.carpooling.dao.RideRepository;
import com.horizon.carpooling.dao.UserRepository;
import com.horizon.carpooling.dto.ride.RideDetailDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RideService {
    private final RideRepository userDao;
    private final ModelMapper mapper;
}
