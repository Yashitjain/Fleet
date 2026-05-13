package com.FYP.Fleet.Dto.Request;

import com.FYP.Fleet.Enums.Method;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {
    private Long amount;
    private LocalDate date;
    private Long ownerId;
    private String note;
    private Method method;

}
