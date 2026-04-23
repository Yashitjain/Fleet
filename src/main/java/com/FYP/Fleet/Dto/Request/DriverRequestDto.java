package com.FYP.Fleet.Dto.Request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DriverRequestDto {

    private String name;
    private String phone;
    private long userId;
}

