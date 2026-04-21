package com.FYP.Fleet.Dto;

import com.FYP.Fleet.Models.User;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleDto {

    private String number;
    private long userId;
}
