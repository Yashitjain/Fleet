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
    private long userId;
    private String userName;
    private String source;
    private String destination;
    private Integer freightPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private Integer totalExpense;
    private Integer profit;
    @Builder.Default
    private List<Expense> expenseList = new ArrayList<>();
}
