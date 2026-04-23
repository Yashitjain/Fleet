package com.FYP.Fleet.Controllers;

import com.FYP.Fleet.Dto.TripDto;
import com.FYP.Fleet.Dto.TripResponseDto;
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
    public ResponseEntity<TripResponseDto> createTrip(@RequestBody TripDto tripDto){
        TripResponseDto trip = tripService.createTrip(tripDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(trip);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripResponseDto> getTrip(@PathVariable long tripId){
        TripResponseDto trip = tripService.getTripResponseById(tripId);
        return ResponseEntity.status(HttpStatus.OK).body(trip);
    }

    @GetMapping("/summary/{tripId}")
    public ResponseEntity<String> getTripSummary(@PathVariable long tripId){
        String summary = tripService.getTripExpenseById(tripId);
        return ResponseEntity.status(HttpStatus.OK).body(summary);
    }
}
