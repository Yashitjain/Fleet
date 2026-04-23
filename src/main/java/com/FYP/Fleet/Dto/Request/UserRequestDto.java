package com.FYP.Fleet.Dto.Request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequestDto {
    private String name;
    private String phone;
    private String password;

}
