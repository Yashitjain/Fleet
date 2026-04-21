package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.TripDto;
import com.FYP.Fleet.Dto.TripResponseDto;
import com.FYP.Fleet.Enums.Status;
import com.FYP.Fleet.Models.Driver;
import com.FYP.Fleet.Models.Trip;
import com.FYP.Fleet.Models.User;
import com.FYP.Fleet.Models.Vehicle;
import com.FYP.Fleet.Repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final UserService userService;

    @Autowired
    public TripService(TripRepository tripRepository, DriverService driverService, VehicleService vehicleService, UserService userService){
        this.tripRepository = tripRepository;
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.userService = userService;
    }

    public TripResponseDto createTrip(TripDto tripDto){
        Driver driver = driverService.getDriverById(tripDto.getDriverId());
        Vehicle vehicle = vehicleService.getVehicleByNumber(tripDto.getVehicleNumber());
        User user = userService.getUserById(tripDto.getUserId());

        Trip trip = Trip.builder()
                .driver(driver)
                .vehicle(vehicle)
                .owner(user)
                .source(tripDto.getSource())
                .destination(tripDto.getDestination())
                .freightPrice(tripDto.getFreightPrice())
                .startDate(tripDto.getStartDate())
                .endDate(tripDto.getEndDate())
                .status(Status.ACTIVE)
                .build();

        trip = tripRepository.save(trip);

        TripResponseDto response = TripResponseDto.builder()
                .id(trip.getId())
                .vehicleNumber(trip.getVehicle().getNumber())
                .ownerId(trip.getOwner().getId())
                .ownerName(trip.getOwner().getName())
                .source(trip.getSource())
                .destination(trip.getDestination())
                .freightPrice(trip.getFreightPrice())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .expenseList(trip.getExpenseList())
                .build();

        return response;
    }


    public TripResponseDto getTripById(long tripId) throws RuntimeException{
        Optional<Trip> tripOptional = tripRepository.findById(tripId);
        if(tripOptional.isEmpty()){
            throw new RuntimeException("Trip Do Not Exist");
        }
        Trip trip = tripOptional.get();
        TripResponseDto response = TripResponseDto.builder()
                .id(tripId)
                .vehicleNumber(trip.getVehicle().getNumber())
                .ownerId(trip.getOwner().getId())
                .ownerName(trip.getOwner().getName())
                .source(trip.getSource())
                .destination(trip.getDestination())
                .freightPrice(trip.getFreightPrice())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .expenseList(trip.getExpenseList())
                .build();

        return response;
    }
}
