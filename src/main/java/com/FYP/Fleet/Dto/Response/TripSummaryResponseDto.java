package com.FYP.Fleet.Dto.Response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TripSummaryResponseDto {
    private int freightPrice;
    private int dieselExpense;
    private int tollExpense;
    private int driverExpense;
    private int otherExpense;
    private int totalExpense;
    private int profit;
}
