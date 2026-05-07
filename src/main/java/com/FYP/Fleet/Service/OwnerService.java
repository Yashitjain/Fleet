package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.Request.OwnerRequestDto;
import com.FYP.Fleet.Dto.Response.OwnerBalanceDto;
import com.FYP.Fleet.Dto.Response.OwnerResponseDto;
import com.FYP.Fleet.Dto.Response.TripSummaryDto;
import com.FYP.Fleet.Enums.ExpenseType;
import com.FYP.Fleet.Models.*;
import com.FYP.Fleet.Repository.ExpenseRepository;
import com.FYP.Fleet.Repository.OwnerRepository;
import com.FYP.Fleet.Repository.TripRepository;
import com.FYP.Fleet.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final UserService userService;
    private final TripService tripService;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, UserService userService,@Lazy TripService tripService){
        this.ownerRepository = ownerRepository;
        this.userService = userService;
        this.tripService = tripService;
    }

    public OwnerResponseDto createOwner(OwnerRequestDto request, Long userId) {
        if (ownerRepository.existsByPhoneAndUserId(request.getPhone(), userId)) {
            throw new RuntimeException("Owner with this phone already exists");
        }

        User user = userService.getUserById(userId);

        Owner owner = new Owner();
        owner.setName(request.getName());
        owner.setPhone(request.getPhone());
        owner.setUser(user);

        Owner saved = ownerRepository.save(owner);
        return mapToResponse(saved);
    }

    public List<OwnerResponseDto> getAllOwners(Long userId) {
        return ownerRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public OwnerResponseDto getOwner(Long ownerId, Long userId) {
        Owner owner = ownerRepository.findByIdAndUserId(ownerId, userId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        return mapToResponse(owner);
    }

    public OwnerBalanceDto getOwnerBalance(Long ownerId, Long userId) {
        Owner owner = ownerRepository.findByIdAndUserId(ownerId, userId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        // Get all vehicle IDs belonging to this owner
        List<Long> vehicleIds = owner.getVehicles()
                .stream()
                .map(Vehicle::getId)
                .collect(Collectors.toList());

        if (vehicleIds.isEmpty()) {
            return emptyBalance(owner);
        }

        // Get all COMPLETED trips for owner's vehicles
        List<Trip> completedTrips = tripService
                .getCompletedTripsByVehicleIds(vehicleIds);

        // Calculate totals
        long totalFreight = completedTrips.stream()
                .mapToLong(Trip::getFreightPrice)
                .sum();

        List<Expense> expenses = completedTrips.stream()
                .flatMap(trip -> trip.getExpenseList().stream())
                .toList();

        long diesel = sumByType(expenses, ExpenseType.DIESEL);
        long toll   = sumByType(expenses, ExpenseType.TOLL);
        long driver = sumByType(expenses, ExpenseType.DRIVER);
        long other  = sumByType(expenses, ExpenseType.OTHER);
        long totalExpenses = diesel + toll + driver + other;
        long balance = totalFreight - totalExpenses;

        OwnerBalanceDto dto = new OwnerBalanceDto();
        dto.setOwnerId(owner.getId());
        dto.setOwnerName(owner.getName());
        dto.setOwnerPhone(owner.getPhone());
        dto.setTotalFreightEarned(totalFreight);
        dto.setDieselExpense(diesel);
        dto.setTollExpense(toll);
        dto.setDriverExpense(driver);
        dto.setOtherExpense(other);
        dto.setTotalExpenses(totalExpenses);
        dto.setBalance(balance);
        dto.setStatus(balance > 0 ? "PAYABLE"
                : balance < 0 ? "RECEIVABLE"
                : "SETTLED");

        // Trip breakdown
        dto.setTrips(completedTrips.stream()
                .map(this::mapToTripSummary)
                .collect(Collectors.toList()));

        return dto;
    }

    private TripSummaryDto mapToTripSummary(Trip trip) {
        int totalExpense = trip.getExpenseList().stream().mapToInt(Expense::getAmount).sum();
        return TripSummaryDto.builder()
                .tripId(trip.getId())
                .vehicleNumber(trip.getVehicle().getNumber())
                .source(trip.getSource())
                .destination(trip.getDestination())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .freightPrice(trip.getFreightPrice())
                .totalExpense(totalExpense)
                .profit(trip.getFreightPrice() - totalExpense)
                .status(trip.getStatus())
                .build();
    }

    private long sumByType(List<Expense> expenses, ExpenseType type) {
        return expenses.stream()
                .filter(e -> e.getExpenseType() == type)
                .mapToLong(Expense::getAmount)
                .sum();
    }

    private OwnerResponseDto mapToResponse(Owner owner) {
        OwnerResponseDto dto = new OwnerResponseDto();
        dto.setOwnerId(owner.getId());
        dto.setName(owner.getName());
        dto.setPhone(owner.getPhone());
        dto.setVehicleNumbers(
                owner.getVehicles().stream()
                        .map(Vehicle::getNumber)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    private OwnerBalanceDto emptyBalance(Owner owner) {
        OwnerBalanceDto dto = new OwnerBalanceDto();
        dto.setOwnerId(owner.getId());
        dto.setOwnerName(owner.getName());
        dto.setBalance(0L);
        dto.setStatus("SETTLED");
        dto.setTrips(new ArrayList<>());
        return dto;
    }

    public Owner getOwnerById(Long ownerId) throws UserPrincipalNotFoundException {
        return ownerRepository.findById(ownerId).orElseThrow(()-> new UserPrincipalNotFoundException("Owner Id Invalid"));
    }
}