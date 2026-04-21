package com.FYP.Fleet.Dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private String name;
    private String phone;
    private String password;

}
