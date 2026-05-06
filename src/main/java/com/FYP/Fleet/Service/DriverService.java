package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.Request.DriverRequestDto;
import com.FYP.Fleet.Dto.Response.DriverResponseDto;
import com.FYP.Fleet.Models.Driver;
import com.FYP.Fleet.Models.Trip;
import com.FYP.Fleet.Models.User;
import com.FYP.Fleet.Repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public DriverResponseDto createDriver(DriverRequestDto driverRequestDto, long userId){
        User owner = userService.getUserById(userId);
        Driver driver = Driver.builder()
                .name(driverRequestDto.getName())
                .phone(driverRequestDto.getPhone())
                .owner(owner)
                .build();

        driver = driverRepository.save(driver);
        owner.getDriverList().add(driver);
        return getDriverResponse(driver);
    }

    public DriverResponseDto getDriverResponseById(long driverId){
        Driver driver = getDriverById(driverId);
        return getDriverResponse(driver);
    }

    public Driver getDriverById(long driverId){
        return driverRepository.findById(driverId).orElseThrow(
                ()->new RuntimeException("Driver Do Not Exists")
        );
    }


    private DriverResponseDto getDriverResponse(Driver driver){
        return DriverResponseDto.builder()
                .id(driver.getId())
                .name(driver.getName())
                .phone(driver.getPhone())
                .ownerId(driver.getOwner().getId())
                .ownerName(driver.getOwner().getName())
                .tripList(driver.getTripList().stream().map(Trip::getId).toList())
                .build();
    }
}
