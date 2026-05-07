package com.FYP.Fleet.Dto.Response;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleResponseDto {

    private long id;
    private String vehicleNumber;
    private long ownerId;
    private List<Long> tripList;
}
