package com.FYP.Fleet.Dto.Request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoginRequestDto {

    private String username;
    private String password;
}
