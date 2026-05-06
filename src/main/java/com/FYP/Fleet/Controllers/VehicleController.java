package com.FYP.Fleet.Controllers;

import com.FYP.Fleet.Dto.Request.VehicleRequestDto;
import com.FYP.Fleet.Dto.Response.VehicleResponseDto;
import com.FYP.Fleet.Models.Vehicle;
import com.FYP.Fleet.Service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    public final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @PostMapping("/")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleRequestDto vehicleRequestDto) throws Exception{
        Vehicle vehicle = vehicleService.createVehicle(vehicleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicle);
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<VehicleResponseDto> getVehicleByNumber(@PathVariable String number) throws Exception{
        VehicleResponseDto vehicle = vehicleService.getVehicleResponseByNumber(number);
        return ResponseEntity.status(HttpStatus.OK).body(vehicle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable long id) throws Exception{
        Vehicle vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(vehicle);
    }
}
