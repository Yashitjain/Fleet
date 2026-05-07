package com.FYP.Fleet.Dto.Response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerBalanceDto {
    private Long ownerId;
    private String ownerName;
    private String ownerPhone;
    private Long totalFreightEarned;
    private Long dieselExpense;
    private Long tollExpense;
    private Long driverExpense;
    private Long otherExpense;
    private Long totalExpenses;
    private Long balance;
    private String status; // PAYABLE, RECEIVABLE, SETTLED
    private List<TripSummaryDto> trips;
}