package com.FYP.Fleet.Dto.MiniResponseDto;

import com.FYP.Fleet.Enums.Status;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MiniTripResponseDto {
    private Long id;
//    private String vehicleNumber;
//    private long driverId;
    private String source;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long freightPrice;
    private Long totalExpense;
    private Long ownerRate;
    private Long profit;
    private Status status;
}
