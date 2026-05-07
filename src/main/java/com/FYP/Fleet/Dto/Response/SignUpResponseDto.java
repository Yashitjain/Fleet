package com.FYP.Fleet.Dto.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SignUpResponseDto {

    private String username;
    private long userId;
}
