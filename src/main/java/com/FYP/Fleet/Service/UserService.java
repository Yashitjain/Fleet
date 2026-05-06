package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.MiniResponseDto.MiniTripResponseDto;
import com.FYP.Fleet.Dto.Request.UserRequestDto;
import com.FYP.Fleet.Dto.Response.UserResponseDto;
import com.FYP.Fleet.Enums.Status;
import com.FYP.Fleet.Models.Driver;
import com.FYP.Fleet.Models.Trip;
import com.FYP.Fleet.Models.User;
import com.FYP.Fleet.Models.Vehicle;
import com.FYP.Fleet.Repository.TripRepository;
import com.FYP.Fleet.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TripService tripService;

    @Autowired
    UserService(UserRepository userRepository,@Lazy TripService tripService){
        this.userRepository = userRepository;
        this.tripService = tripService;
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto, long userId){
        User user = userRepository.findById(userId).orElseThrow(()->new  UsernameNotFoundException("UserId is invalid"));
        user.setName(userRequestDto.getName());
        user.setPhone(userRequestDto.getPhone());
        user = userRepository.save(user);
        return getUserResponse(user);
    }

    public UserResponseDto getUserResponseById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User do not exist"));
        return getUserResponse(user);

    }

    public User getUserById(long userId){
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User do not exist"));
    }

    private UserResponseDto getUserResponse(User user){
        return UserResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .vehicleList(user.getVehicleList().stream().map(Vehicle::getNumber).toList())
                .driverList(user.getDriverList().stream().map(Driver::getName).toList())
                .tripList(user.getTripList().stream().map(Trip::getId).toList())
                .build();
    }

    public Integer getBalance(Long ownerId) {
        List<MiniTripResponseDto > tripList = tripService.getTripsOfOwner(ownerId);
        return tripList.stream().filter(t->t.getStatus().equals(Status.COMPLETED)).mapToInt(MiniTripResponseDto::getProfit).sum();
    }
}
