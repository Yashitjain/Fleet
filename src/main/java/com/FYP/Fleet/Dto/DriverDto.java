package com.FYP.Fleet.Dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DriverDto {

    private String name;
    private String phone;
    private long userId;
}

