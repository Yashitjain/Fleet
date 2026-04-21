package com.FYP.Fleet.Controllers;

import com.FYP.Fleet.Dto.TripDto;
import com.FYP.Fleet.Models.Trip;
import com.FYP.Fleet.Service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trip")
public class TripController {

    public final TripService tripService;

    public TripController(TripService tripService){
        this.tripService = tripService;
    }

    @PostMapping("/")
    public ResponseEntity<Trip> createTrip(@RequestBody TripDto tripDto){
        Trip trip = tripService.createTrip(tripDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(trip);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<Trip> getTrip(@PathVariable long tripId){
        Trip trip = tripService.getTripById(tripId);
        return ResponseEntity.status(HttpStatus.OK).body(trip);
    }
}
