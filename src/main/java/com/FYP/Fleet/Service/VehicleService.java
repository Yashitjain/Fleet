package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.Request.VehicleRequestDto;
import com.FYP.Fleet.Dto.Response.VehicleResponseDto;
import com.FYP.Fleet.Models.Trip;
import com.FYP.Fleet.Models.User;
import com.FYP.Fleet.Models.Vehicle;
import com.FYP.Fleet.Repository.UserRepository;
import com.FYP.Fleet.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    VehicleService(VehicleRepository vehicleRepository, UserRepository userRepository, UserService userService){
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }
    @Transactional
    public Vehicle createVehicle(VehicleRequestDto vehicleRequestDto, long userId) {
        User user = userService.getUserById(userId);
        Vehicle createVehicle = Vehicle.builder()
                .number(vehicleRequestDto.getVehicleNumber())
                .user(user)
                .build();

        createVehicle = vehicleRepository.save(createVehicle);
        user.getVehicleList().add(createVehicle);
        userRepository.save(user);

        return createVehicle;

    }

    public VehicleResponseDto getVehicleByVehicleNumber(String number) throws RuntimeException{
        Vehicle vehicle = getVehicleByNumber(number);
        return VehicleResponseDto.builder()
                .id(vehicle.getId())
                .vehicleNumber(vehicle.getNumber())
                .tripList(vehicle.getTripList().stream().map(Trip::getId).toList())
                .build();

    }

    public Vehicle getVehicleById(long id){
        return vehicleRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Vehicle Id Do Not Exist")
        );
    }

    public Vehicle getVehicleByNumber(String number){
        return vehicleRepository.findByNumber(number).orElseThrow(
                ()-> new RuntimeException("Vehicle Number Do Not Exist")
        );
    }


    public List<VehicleResponseDto> getAllVehicleOfOwner(Long userId) {
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        return vehicleList.stream().filter(v -> v.getUser().getId().equals(userId)).map(this::getVehicleResponse).toList();
    }

    private VehicleResponseDto getVehicleResponse(Vehicle vehicle){
        return VehicleResponseDto.builder()
                .id(vehicle.getId())
                .vehicleNumber(vehicle.getNumber())
                .tripList(vehicle.getTripList().stream().map(Trip::getId).toList())
                .build();
    }
}
