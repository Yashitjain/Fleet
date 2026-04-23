package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.ExpenseDto;
import com.FYP.Fleet.Dto.ExpenseResponseDto;
import com.FYP.Fleet.Dto.TripResponseDto;
import com.FYP.Fleet.Models.Expense;
import com.FYP.Fleet.Models.Trip;
import com.FYP.Fleet.Repository.ExpenseRepository;
import com.FYP.Fleet.Repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final TripService tripService;
    private final TripRepository tripRepository;


    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, TripService tripService, TripRepository tripRepository){
        this.expenseRepository = expenseRepository;
        this.tripService = tripService;
        this.tripRepository = tripRepository;
    }

    @Transactional
    public ExpenseResponseDto createExpense(ExpenseDto expenseDto){
        Trip trip = tripRepository.findById(expenseDto.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        Expense expense = Expense.builder()
                .trip(trip)
                .expenseType(expenseDto.getExpenseType())
                .note(expenseDto.getNote())
                .amount(expenseDto.getAmount())
                .date(expenseDto.getDate())
                .build();

        expense = expenseRepository.save(expense);
        trip.getExpenseList().add(expense);

        return ExpenseResponseDto.builder()
                .expenseId(expense.getId())
                .tripId(trip.getId())
                .driverId(trip.getDriver().getId())
                .ownerId(trip.getOwner().getId())
                .ownerName(trip.getOwner().getName())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .source(trip.getSource())
                .destination(trip.getDestination())
                .note(expense.getNote())
                .expenseType(expense.getExpenseType())
                .build();
    }

    public ExpenseResponseDto getExpenseById(long expenseId){
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense Not Found"));

        return ExpenseResponseDto.builder()
                .expenseId(expense.getId())
                .tripId(expense.getTrip().getId())
                .driverId(expense.getTrip().getDriver().getId())
                .ownerId(expense.getTrip().getOwner().getId())
                .ownerName(expense.getTrip().getOwner().getName())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .source(expense.getTrip().getSource())
                .destination(expense.getTrip().getDestination())
                .note(expense.getNote())
                .expenseType(expense.getExpenseType())
                .build();
    }
}
