package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.Request.OwnerRequestDto;
import com.FYP.Fleet.Dto.Response.OwnerBalanceDto;
import com.FYP.Fleet.Dto.Response.OwnerBalanceTripSummaryResponseDto;
import com.FYP.Fleet.Dto.Response.OwnerResponseDto;
import com.FYP.Fleet.Dto.Response.TripSummaryDto;
import com.FYP.Fleet.Enums.ExpenseType;
import com.FYP.Fleet.Models.*;
import com.FYP.Fleet.Repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
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

        List<Trip> trips = tripService
                .getTripsByUserIdAndOwnerId(userId, ownerId);

        List<Trip> unSettledTrips = trips.stream().filter(t -> !t.getSettled()).toList();

        long totalAdvance = unSettledTrips.stream().mapToLong(Trip::getOwnerAdvance).sum();
        long totalPay = unSettledTrips.stream().mapToLong(Trip::getOwnerRate).sum();
        long revenue = trips.stream().mapToLong(Trip::getOwnerRate).sum();
        long amountToPay = totalPay - totalAdvance;

        OwnerBalanceDto dto = new OwnerBalanceDto();
        dto.setOwnerId(owner.getId());
        dto.setOwnerName(owner.getName());
        dto.setOwnerPhone(owner.getPhone());
        dto.setTotalPay(revenue);
        dto.setTotalAdvance(totalAdvance);
        dto.setAmountToPay(amountToPay);
        dto.setStatus(amountToPay > 0 ? "RECEIVABLE"
                : amountToPay < 0 ? "PAYABLE"
                : "SETTLED");

        dto.setTrips(trips.stream()
                .map(this::mapToOwnerBalanceTripSummaryResponseDto)
                .collect(Collectors.toList()));

        return dto;
    }

    private TripSummaryDto mapToTripSummary(Trip trip) {
        Long totalExpense = trip.getExpenseList().stream().mapToLong(Expense::getAmount).sum();
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

    private OwnerBalanceTripSummaryResponseDto mapToOwnerBalanceTripSummaryResponseDto(Trip trip) {
        return OwnerBalanceTripSummaryResponseDto.builder()
                .tripId(trip.getId())
                .rate(trip.getOwnerRate())
                .source(trip.getSource())
                .destination(trip.getDestination())
                .settled(trip.getSettled())
                .vehicleNumber(trip.getVehicle().getNumber())
                .advance(trip.getOwnerAdvance())
                .build();
    }

    public Owner getOwnerById(Long ownerId) throws UserPrincipalNotFoundException {
        return ownerRepository.findById(ownerId).orElseThrow(()-> new UserPrincipalNotFoundException("Owner Id Invalid"));
    }
}