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
    private Integer freightPrice;
    private Integer totalExpense;
    private int profit;
    private Status status;
}
