package com.FYP.Fleet.Dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleResponseDto {

    private long id;
    private String number;
    private List<Long> tripList;
}
