package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.MiniResponseDto.MiniTripResponseDto;
import com.FYP.Fleet.Dto.Request.UserRequestDto;
import com.FYP.Fleet.Dto.Response.UserBalanceResponseDto;
import com.FYP.Fleet.Dto.Response.UserResponseDto;
import com.FYP.Fleet.Enums.ExpenseType;
import com.FYP.Fleet.Models.*;
import com.FYP.Fleet.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TripService tripService;

    @Autowired
    UserService(UserRepository userRepository,@Lazy TripService tripService){
        this.userRepository = userRepository;
        this.tripService = tripService;
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto, long userId){
        User user = userRepository.findById(userId).orElseThrow(()->new  UsernameNotFoundException("UserId is invalid"));
        user.setName(userRequestDto.getName());
        user.setPhone(userRequestDto.getPhone());
        user = userRepository.save(user);
        return getUserResponse(user);
    }

    public UserResponseDto getUserResponseById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User do not exist"));
        return getUserResponse(user);

    }

    public User getUserById(long userId){
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User do not exist"));
    }

    private UserResponseDto getUserResponse(User user){
        return UserResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .vehicleList(user.getVehicleList().stream().map(Vehicle::getNumber).toList())
                .driverList(user.getDriverList().stream().map(Driver::getName).toList())
                .tripList(user.getTripList().stream().map(Trip::getId).toList())
                .build();
    }

    public UserBalanceResponseDto getBalance(Long userId) {
        List<Trip> tripList = tripService.getCompletedTripsByUserId(userId);
        List<MiniTripResponseDto> miniTripResponseDos = tripList.stream().map(tripService::getMiniTripResponse).toList();

        Long totalFreightPrice = tripList.stream().mapToLong(Trip::getFreightPrice).sum();
        Long dieselExpense = tripList.stream().mapToLong(t -> sumByType(t.getExpenseList(), ExpenseType.DIESEL)).sum();
        Long tollExpense = tripList.stream().mapToLong(t -> sumByType(t.getExpenseList(), ExpenseType.TOLL)).sum();
        Long driverExpense = tripList.stream().mapToLong(t -> sumByType(t.getExpenseList(), ExpenseType.DRIVER)).sum();
        Long otherExpense = tripList.stream().mapToLong(t -> sumByType(t.getExpenseList(), ExpenseType.OTHER)).sum();
        Long totalExpense = dieselExpense + tollExpense + driverExpense + otherExpense;
        Long ownerRate = tripList.stream().mapToLong(Trip::getOwnerRate).sum();
        Long ownerAdvance = tripList.stream().mapToLong(Trip::getOwnerAdvance).sum();
        Long amountToPay = ownerRate - ownerAdvance;
        Long profit = totalFreightPrice - totalExpense - (ownerRate - ownerAdvance) ;

        return UserBalanceResponseDto.builder()
                .totalTrips(tripList.size())
                .totalFreightEarned(totalFreightPrice)
                .dieselExpense(dieselExpense)
                .tollExpense(tollExpense)
                .driverExpense(driverExpense)
                .otherExpense(otherExpense)
                .totalExpenses(totalExpense)
                .ownerRate(ownerRate)
                .ownerAdvance(ownerAdvance)
                .amountToPay(amountToPay)
                .profit(profit)
                .trips(miniTripResponseDos)
                .build();

    }

    private Long sumByType(List<Expense> expenses, ExpenseType type) {
        return expenses.stream()
                .filter(e -> e.getExpenseType() == type)
                .mapToLong(Expense::getAmount)
                .sum();
    }

}
