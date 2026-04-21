package com.FYP.Fleet.Dto;

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
    private long ownerId;
    private String ownerName;
    private String source;
    private String destination;
    private Integer freightPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    @Builder.Default
    private List<Expense> expenseList = new ArrayList<>();
}
