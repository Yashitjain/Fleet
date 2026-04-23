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
    private long userId;
    private String source;
    private String destination;
    private Integer freightPrice;
    private LocalDate startDate;
    private LocalDate endDate;
}
