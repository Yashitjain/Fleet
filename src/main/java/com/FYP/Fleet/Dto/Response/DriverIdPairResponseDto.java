package com.FYP.Fleet.Dto.Response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DriverIdPairResponseDto {
    private Long driverId;
    private String driverName;
}
