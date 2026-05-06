package com.FYP.Fleet.Dto.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoginResponseDto {

    private String jwt;
    private long userId;
}
