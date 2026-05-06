package com.FYP.Fleet.Controllers;

import com.FYP.Fleet.Dto.Request.TripRequestDto;
import com.FYP.Fleet.Dto.Response.TripResponseDto;
import com.FYP.Fleet.Models.SecurityUser;
import com.FYP.Fleet.Service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip")
public class TripController {

    public final TripService tripService;

    public TripController(TripService tripService){
        this.tripService = tripService;
    }

    @PostMapping("/")
    public ResponseEntity<TripResponseDto> createTrip(@RequestBody TripRequestDto tripRequestDto, @AuthenticationPrincipal SecurityUser securityUser){
        System.out.println(securityUser.getId());
        TripResponseDto trip = tripService.createTrip(tripRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(trip);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripResponseDto> getTrip(@PathVariable long tripId, @AuthenticationPrincipal SecurityUser securityUser){
        System.out.println(securityUser.getId());
        TripResponseDto trip = tripService.getTripResponseById(tripId);
        return ResponseEntity.status(HttpStatus.OK).body(trip);
    }

    @GetMapping("/summary/{tripId}")
    public ResponseEntity<String> getTripSummary(@PathVariable long tripId){
        String summary = tripService.getTripExpenseById(tripId);
        return ResponseEntity.status(HttpStatus.OK).body(summary);
    }
}
