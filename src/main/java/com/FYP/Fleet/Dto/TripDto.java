package com.FYP.Fleet.Dto;

import com.FYP.Fleet.Models.Expense;
import lombok.*;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TripDto {

    private long driverId;
    private String vehicleNumber;
    private long userId;
    private String source;
    private String destination;
    private Integer freightPrice;
    private LocalDate startDate;
    private LocalDate endDate;
}
