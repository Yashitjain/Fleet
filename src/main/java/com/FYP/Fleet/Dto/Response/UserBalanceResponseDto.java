package com.FYP.Fleet.Dto.Response;

import com.FYP.Fleet.Dto.MiniResponseDto.MiniTripResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBalanceResponseDto {
    private int totalTrips;
    private Long totalFreightEarned;
    private Long dieselExpense;
    private Long tollExpense;
    private Long driverExpense;
    private Long otherExpense;
    private Long totalExpenses;
    private Long ownerRate;
    private Long ownerAdvance;
    private Long amountToPay;
    private Long profit;
    private List<MiniTripResponseDto> trips;
}
