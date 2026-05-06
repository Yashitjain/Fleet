package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.MiniResponseDto.MiniExpenseResponseDto;
import com.FYP.Fleet.Dto.Request.ExpenseRequestDto;
import com.FYP.Fleet.Dto.Response.ExpenseResponseDto;
import com.FYP.Fleet.Models.Expense;
import com.FYP.Fleet.Models.Trip;
import com.FYP.Fleet.Repository.ExpenseRepository;
import com.FYP.Fleet.Repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public MiniExpenseResponseDto createExpense(ExpenseRequestDto expenseRequestDto){
        Trip trip = tripRepository.findById(expenseRequestDto.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        Expense expense = Expense.builder()
                .trip(trip)
                .expenseType(expenseRequestDto.getExpenseType())
                .note(expenseRequestDto.getNote())
                .amount(expenseRequestDto.getAmount())
                .date(expenseRequestDto.getDate())
                .build();

        expense = expenseRepository.save(expense);
        trip.getExpenseList().add(expense);

        return getMiniExpenseResponseDto(expense);
    }

    public ExpenseResponseDto getExpenseResponseById(long expenseId){
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense Not Found"));
        return getExpenseResponse(expense);

    }

    public void updateResponse(long expenseId, ExpenseRequestDto expenseRequestDto){
        Expense expense = getExpenseById(expenseId);
        if(expenseRequestDto.getExpenseType() != null) expense.setExpenseType(expenseRequestDto.getExpenseType());
        if(expenseRequestDto.getNote() != null) expense.setNote(expenseRequestDto.getNote());
        if(expenseRequestDto.getDate() != null) expense.setDate(expenseRequestDto.getDate());
        if(expenseRequestDto.getAmount() != null) expense.setAmount(expenseRequestDto.getAmount());
        expenseRepository.save(expense);

    }

    private Expense getExpenseById(long expenseId){
        return expenseRepository.findById(expenseId).orElseThrow(
                () -> new RuntimeException("Expense Id Do Not Exist")
        );
    }

    public List<MiniExpenseResponseDto> getTripAllExpense(long tripId) {
        Trip trip = tripService.getTripById(tripId);
        List<Expense> expenseList = trip.getExpenseList();
        return expenseList.stream().map(this::getMiniExpenseResponseDto).toList();
    }

    private ExpenseResponseDto getExpenseResponse(Expense expense){
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

    private MiniExpenseResponseDto getMiniExpenseResponseDto(Expense expense){
        return MiniExpenseResponseDto.builder()
                .expenseId(expense.getId())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .note(expense.getNote())
                .expenseType(expense.getExpenseType())
                .build();
    }
}
