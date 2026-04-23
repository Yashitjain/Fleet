package com.FYP.Fleet.Dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponseDto {

    private long userId;
    private String name;
    private String phone;
    private List<String> vehicleList;
    private List<String> driverList;
    private List<Long> tripList;


}
