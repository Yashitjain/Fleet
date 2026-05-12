package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.MiniResponseDto.MiniTripResponseDto;
import com.FYP.Fleet.Dto.Request.TripRequestDto;
import com.FYP.Fleet.Dto.Response.TripResponseDto;
import com.FYP.Fleet.Dto.Response.TripStatusResponseDto;
import com.FYP.Fleet.Dto.Response.TripSummaryResponseDto;
import com.FYP.Fleet.Enums.ExpenseType;
import com.FYP.Fleet.Enums.Status;
import com.FYP.Fleet.Models.*;
import com.FYP.Fleet.Repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public TripResponseDto  createTrip(TripRequestDto tripRequestDto, long userId){
        Driver driver = driverService.getDriverById(tripRequestDto.getDriverId());
        Vehicle vehicle = vehicleService.getVehicleByNumber(tripRequestDto.getVehicleNumber());
        User owner = userService.getUserById(userId);

        Trip trip = Trip.builder()
                .driver(driver)
                .vehicle(vehicle)
                .user(owner)
                .source(tripRequestDto.getSource())
                .destination(tripRequestDto.getDestination())
                .freightPrice(tripRequestDto.getFreightPrice())
                .startDate(tripRequestDto.getStartDate())
                .endDate(tripRequestDto.getEndDate())
                .status(Status.ACTIVE)
                .ownerRate(tripRequestDto.getOwnerRate())
                .ownerAdvance(tripRequestDto.getOwnerAdvance())
                .build();

        trip = tripRepository.save(trip);
        vehicle.getTripList().add(trip);
        owner.getTripList().add(trip);
        driver.getTripList().add(trip);
        return getTripResponse(trip);
    }


    public TripResponseDto getTripResponseById(long tripId) throws RuntimeException{
        Optional<Trip> tripOptional = tripRepository.findById(tripId);
        if(tripOptional.isEmpty()){
            throw new RuntimeException("Trip Do Not Exist");
        }
        Trip trip = tripOptional.get();
        return getTripResponse(trip);

    }

    public TripSummaryResponseDto getTripSummaryById(long tripId){
        Trip trip = getTripById(tripId);

        Long freightPrice = trip.getFreightPrice();

        Long dieselExpense = getExpenseByCategory(ExpenseType.DIESEL, trip);
        Long tollExpense = getExpenseByCategory(ExpenseType.TOLL, trip);
        Long driverExpense = getExpenseByCategory(ExpenseType.DRIVER, trip);
        Long otherExpense = getExpenseByCategory(ExpenseType.OTHER, trip);
        Long ownerRate = trip.getOwnerRate();
        Long totalExpense = dieselExpense + tollExpense + driverExpense + otherExpense;

        Long profit = freightPrice - totalExpense - ownerRate;
        return TripSummaryResponseDto.builder()
                .freightPrice(freightPrice)
                .dieselExpense(dieselExpense)
                .tollExpense(tollExpense)
                .driverExpense(driverExpense)
                .otherExpense(otherExpense)
                .totalExpense(totalExpense)
                .profit(profit)
                .ownerRate(ownerRate)
                .build();
    }

    public Trip getTripById(long tripId){
        return tripRepository.findById(tripId).orElseThrow(
                ()-> new RuntimeException("Trip Id Invalid"));
    }

    public Long getTotalExpenseOfTrip(Trip trip){
        Long dieselExpense = getExpenseByCategory(ExpenseType.DIESEL, trip);
        Long tollExpense = getExpenseByCategory(ExpenseType.TOLL, trip);
        Long driverExpense = getExpenseByCategory(ExpenseType.DRIVER, trip);
        Long otherExpense = getExpenseByCategory(ExpenseType.OTHER, trip);

        return dieselExpense + tollExpense + driverExpense + otherExpense;
    }

    public Long getTotalProfitOfTrip(Trip trip){
        Long freightPrice = trip.getFreightPrice();
        Long totalExpense = getTotalExpenseOfTrip(trip);
        return freightPrice - totalExpense - trip.getOwnerRate();
    }

    public Long getExpenseByCategory(ExpenseType expenseType, Trip trip){
        return trip.getExpenseList().stream().filter(t -> t.getExpenseType().equals(expenseType)).mapToLong(Expense::getAmount).sum();
    }


    public List<MiniTripResponseDto> getTripsOfOwner(Long userId) {
        List<Trip> trips = tripRepository.findAll();
        return trips.stream().filter(t -> t.getUser().getId().equals(userId)).map(this::getMiniTripResponse).toList();

    }

    private TripResponseDto getTripResponse(Trip trip){
        return TripResponseDto.builder()
                .id(trip.getId())
                .driverName(trip.getDriver().getName())
                .vehicleNumber(trip.getVehicle().getNumber())
                .driverId(trip.getDriver().getId())
                .userId(trip.getUser().getId())
                .userName(trip.getUser().getName())
                .source(trip.getSource())
                .destination(trip.getDestination())
                .freightPrice(trip.getFreightPrice())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .expenseList(trip.getExpenseList())
                .totalExpense(getTotalExpenseOfTrip(trip))
                .ownerRate(trip.getOwnerRate())
                .profit(getTotalProfitOfTrip(trip))
                .status(trip.getStatus())
                .build();
    }

    public String getTripStatus(long tripId) {
        Trip trip = getTripById(tripId);
        return trip.getStatus().name();
    }

    public MiniTripResponseDto getMiniTripResponse(Trip trip){
        return MiniTripResponseDto.builder()
                .id(trip.getId())
                .driverName(trip.getDriver().getName())
                .vehicleNumber(trip.getVehicle().getNumber())
                .source(trip.getSource())
                .destination(trip.getDestination())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .freightPrice(trip.getFreightPrice())
                .totalExpense(getTotalExpenseOfTrip(trip))
                .status(trip.getStatus())
                .profit(getTotalProfitOfTrip(trip))
                .ownerRate(trip.getOwnerRate())
                .build();
    }

    public TripStatusResponseDto closeTrip(long tripId) {
        Trip trip = getTripById(tripId);
        trip.setStatus(Status.COMPLETED);
        trip = tripRepository.save(trip);
        return TripStatusResponseDto.builder()
                .tripId(tripId)
                .status(trip.getStatus())
                .build();
    }

    public List<Trip> getCompletedTripsByVehicleIds(List<Long> vehicleIds) {
        return tripRepository.findCompletedTripsByVehicleIds(vehicleIds);
    }

    public List<Expense> getByIdIn(List<Long> tripIds) {
        return tripRepository.findByIdIn(tripIds);
    }

    public List<Trip> getTripsByUserIdAndOwnerId(Long userId, Long ownerId) {
        return tripRepository.getTripsByUserIdAndOwnerId(userId, ownerId);
    }

    public List<Trip> getCompletedTripsByOwnerIdAndUserId(Long ownerId, Long userId) {
        return tripRepository.getCompletedTripsByOwnerIdAndUserId(ownerId, userId);
    }

    public Integer totalActiveTripsCount(Long userId) {
        return tripRepository.findByUserId(userId).size();
    }

    public List<Trip> getTripByUserId(Long userId){
        return tripRepository.findByUserId(userId);
    }

    public List<Trip> getActiveTripsByUserId(Long userId){
        return tripRepository.findActiveTripsByUserId(userId);
    }

    public void settleTripPayment(long tripId, long userId) {
        Trip trip = getTripByIdAndUserId(tripId, userId);
        trip.setSettled(true);
        trip.setOwnerAdvance(trip.getOwnerRate());
        tripRepository.save(trip);
    }

    public Trip getTripByIdAndUserId(long tripId, long userId){
        return tripRepository.findByIdAndUserId(tripId, userId).orElseThrow(() -> new RuntimeException("Trip Not Found"));
    }
}
