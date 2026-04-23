package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.VehicleDto;
import com.FYP.Fleet.Models.User;
import com.FYP.Fleet.Models.Vehicle;
import com.FYP.Fleet.Repository.UserRepository;
import com.FYP.Fleet.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public Vehicle createVehicle(VehicleDto vehicleDto) throws Exception{
        User owner = userService.getUserById(vehicleDto.getUserId());
        Vehicle createVehicle = Vehicle.builder()
                .number(vehicleDto.getNumber())
                .owner(owner)
                .build();

        createVehicle = vehicleRepository.save(createVehicle);
        owner.getVehicleList().add(createVehicle);
        userRepository.save(owner);

        return createVehicle;

    }

    public Vehicle getVehicleByNumber(String number) throws RuntimeException{
        Optional<Vehicle> vehicle = vehicleRepository.findByNumber(number);
        if(vehicle.isEmpty()){
            throw new RuntimeException("Vehicle Do Not Exist");
        }

        return vehicle.get();
    }


}
