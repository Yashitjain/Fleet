package com.FYP.Fleet.Dto.MiniResponseDto;

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
