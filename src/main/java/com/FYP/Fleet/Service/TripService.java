package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.Request.TripRequestDto;
import com.FYP.Fleet.Dto.Response.TripResponseDto;
import com.FYP.Fleet.Enums.Status;
import com.FYP.Fleet.Models.*;
import com.FYP.Fleet.Repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public TripResponseDto createTrip(TripRequestDto tripRequestDto){
        Driver driver = driverService.getDriverById(tripRequestDto.getDriverId());
        Vehicle vehicle = vehicleService.getVehicleByNumber(tripRequestDto.getVehicleNumber());
        User owner = userService.getUserById(tripRequestDto.getUserId());

        Trip trip = Trip.builder()
                .driver(driver)
                .vehicle(vehicle)
                .owner(owner)
                .source(tripRequestDto.getSource())
                .destination(tripRequestDto.getDestination())
                .freightPrice(tripRequestDto.getFreightPrice())
                .startDate(tripRequestDto.getStartDate())
                .endDate(tripRequestDto.getEndDate())
                .status(Status.ACTIVE)
                .build();

        trip = tripRepository.save(trip);
        vehicle.getTripList().add(trip);
        owner.getTripList().add(trip);
        driver.getTripList().add(trip);

        return TripResponseDto.builder()
                .id(trip.getId())
                .vehicleNumber(trip.getVehicle().getNumber())
                .driverId(trip.getDriver().getId())
                .ownerId(trip.getOwner().getId())
                .ownerName(trip.getOwner().getName())
                .source(trip.getSource())
                .destination(trip.getDestination())
                .freightPrice(trip.getFreightPrice())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .expenseList(trip.getExpenseList())
                .build();

    }


    public TripResponseDto getTripResponseById(long tripId) throws RuntimeException{
        Optional<Trip> tripOptional = tripRepository.findById(tripId);
        if(tripOptional.isEmpty()){
            throw new RuntimeException("Trip Do Not Exist");
        }
        Trip trip = tripOptional.get();
        return TripResponseDto.builder()
                .id(tripId)
                .vehicleNumber(trip.getVehicle().getNumber())
                .driverId(trip.getDriver().getId())
                .ownerId(trip.getOwner().getId())
                .ownerName(trip.getOwner().getName())
                .source(trip.getSource())
                .destination(trip.getDestination())
                .freightPrice(trip.getFreightPrice())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .expenseList(trip.getExpenseList())
                .build();

    }

    public String getTripExpenseById(long tripId){
        Trip trip = getTripId(tripId);

        Integer totalExpense = trip.getExpenseList().stream().mapToInt(Expense::getAmount).sum();
        Integer freightPrice = trip.getFreightPrice();
        int balance = freightPrice - totalExpense;

        return "Freight Price: " + freightPrice +
                "\nTotal Expense: " + totalExpense +
                "\nProfit: " + balance;
    }

    private Trip getTripId(long tripId){
        return tripRepository.findById(tripId).orElseThrow(
                ()-> new RuntimeException("Trip Id Invalid"));
    }

    private int getTotalExpenseByTripId(long tripId){
        Trip trip = getTripId(tripId);
        return trip.getExpenseList().stream().mapToInt(Expense::getAmount).sum();
    }

}
