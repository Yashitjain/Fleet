package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.VehicleDto;
import com.FYP.Fleet.Models.User;
import com.FYP.Fleet.Models.Vehicle;
import com.FYP.Fleet.Repository.UserRepository;
import com.FYP.Fleet.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Autowired
    VehicleService(VehicleRepository vehicleRepository, UserRepository userRepository){
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    public Vehicle createVehicle(VehicleDto vehicleDto){
        Vehicle createVehicle = Vehicle.builder()
                .number(vehicleDto.getNumber())
                .user(vehicleDto.getUser())
                .build();

        Optional<User> user = userRepository.findById(vehicleDto.getUser().getId());
        if(user.isEmpty()){
            throw new RuntimeException("No User Exist with id : " + vehicleDto.getUser().getId());
        }

        createVehicle = vehicleRepository.save(createVehicle);
        vehicleDto.getUser().getVehicleList().add(createVehicle);
        userRepository.save(vehicleDto.getUser());

        return createVehicle;

    }

    public Vehicle getVehicleByNumber(String number) throws Exception{
        Optional<Vehicle> vehicle = vehicleRepository.findByNumber(number);
        if(vehicle.isEmpty()){
            throw new RuntimeException("Vehicle Do Not Exist");
        }

        return vehicle.get();
    }


}
