package com.FYP.Fleet.Dto.Request;

import lombok.*;
import org.springframework.stereotype.Service;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String username;
    private String password;
}
