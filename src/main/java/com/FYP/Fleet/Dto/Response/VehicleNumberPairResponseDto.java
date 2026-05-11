package com.FYP.Fleet.Dto.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class VehicleNumberPairResponseDto {
    private Long vehicleId;
    private String vehicleNumber;
}
