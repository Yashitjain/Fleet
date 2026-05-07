package com.FYP.Fleet.Dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VehicleRequestDto {

    @NotNull
    private String vehicleNumber;

    private Long ownerId;
}
