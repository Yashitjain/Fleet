package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.DriverDto;
import com.FYP.Fleet.Dto.DriverResponseDto;
import com.FYP.Fleet.Models.Driver;
import com.FYP.Fleet.Models.Trip;
import com.FYP.Fleet.Models.User;
import com.FYP.Fleet.Repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DriverService {

    public final DriverRepository driverRepository;
    public final UserService userService;

    @Autowired
    public DriverService(DriverRepository driverRepository, UserService userService){
        this.driverRepository = driverRepository;
        this.userService = userService;
    }

    @Transactional
    public Driver createDriver(DriverDto driverDto){
        User user = userService.getUserById(driverDto.getUserId());
        Driver driver = Driver.builder()
                .name(driverDto.getName())
                .phone(driverDto.getPhone())
                .owner(user)
                .build();

        driver = driverRepository.save(driver);
        user.getDriverList().add(driver);
        return driver;
    }

    public DriverResponseDto getDriverResponseById(long driverId){
        Driver driver = getDriverById(driverId);

        return DriverResponseDto.builder()
                .id(driverId)
                .name(driver.getName())
                .phone(driver.getPhone())
                .ownerId(driver.getOwner().getId())
                .ownerName(driver.getOwner().getName())
                .tripList(driver.getTripList().stream().map(Trip::getId).toList())
                .build();
    }

    public Driver getDriverById(long driverId){
        return driverRepository.findById(driverId).orElseThrow(
                ()->new RuntimeException("Driver Do Not Exists")
        );
    }
}
