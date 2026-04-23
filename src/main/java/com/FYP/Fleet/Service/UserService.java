package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.Request.UserRequestDto;
import com.FYP.Fleet.Dto.Response.UserResponseDto;
import com.FYP.Fleet.Models.Driver;
import com.FYP.Fleet.Models.Trip;
import com.FYP.Fleet.Models.User;
import com.FYP.Fleet.Models.Vehicle;
import com.FYP.Fleet.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(UserRequestDto userRequestDto){
        User createuser = User.builder()
                .name(userRequestDto.getName())
                .phone(userRequestDto.getPhone())
                .password(userRequestDto.getPassword())
                .build();

        createuser = userRepository.save(createuser);
        return createuser;
    }

    public UserResponseDto getUserResponseById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User do not exist"));
        return UserResponseDto.builder()
                .name(user.getName())
                .phone(user.getPhone())
                .vehicleList(user.getVehicleList().stream().map(Vehicle::getNumber).toList())
                .driverList(user.getDriverList().stream().map(Driver::getName).toList())
                .tripList(user.getTripList().stream().map(Trip::getId).toList())
                .build();

    }


    public User getUserById(long userId){
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User do not exist"));
    }
}
