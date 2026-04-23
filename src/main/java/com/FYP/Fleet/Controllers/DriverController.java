package com.FYP.Fleet.Controllers;

import com.FYP.Fleet.Dto.DriverDto;
import com.FYP.Fleet.Dto.DriverResponseDto;
import com.FYP.Fleet.Models.Driver;
import com.FYP.Fleet.Repository.DriverRepository;
import com.FYP.Fleet.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {

    public final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }

    @PostMapping("/")
    public ResponseEntity<Driver> createDriver(@RequestBody DriverDto driverDto){
        Driver driver = driverService.createDriver(driverDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverResponseDto> getDriverById(@PathVariable long driverId){
        DriverResponseDto driver = driverService.getDriverResponseById(driverId);
        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }
}
