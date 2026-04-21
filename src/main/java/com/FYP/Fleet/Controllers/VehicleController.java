package com.FYP.Fleet.Controllers;

import com.FYP.Fleet.Dto.VehicleDto;
import com.FYP.Fleet.Models.Vehicle;
import com.FYP.Fleet.Service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

    public final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @PostMapping("/")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleDto vehicleDto) throws Exception{
        Vehicle vehicle = vehicleService.createVehicle(vehicleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicle);
    }

    @GetMapping("/{number}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable String number) throws Exception{
        Vehicle vehicle = vehicleService.getVehicleByNumber(number);
        return ResponseEntity.status(HttpStatus.OK).body(vehicle);
    }
}
