package com.FYP.Fleet.Dto.Request;
import lombok.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TripRequestDto {

    private long driverId;
    private String vehicleNumber;
    private String source;
    private String destination;
    private Long freightPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long ownerRate;
    private Long ownerAdvance;
}
