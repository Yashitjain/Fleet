package com.FYP.Fleet.Dto.Response;

import com.FYP.Fleet.Enums.Status;
import com.FYP.Fleet.Models.Expense;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TripResponseDto {

    private Long id;
    private String vehicleNumber;
    private long driverId;
    private String driverName;
    private long userId;
    private String userName;
    private String source;
    private String destination;
    private Long freightPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private Long totalExpense;
    private Long ownerRate;
    private Long profit;
    @Builder.Default
    private List<Expense> expenseList = new ArrayList<>();
}
