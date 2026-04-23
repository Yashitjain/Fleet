package com.FYP.Fleet.Dto.Request;

import com.FYP.Fleet.Enums.ExpenseType;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExpenseRequestDto {
    private long tripId;
    private ExpenseType expenseType;
    private String note;
    private LocalDate date;
    private Integer amount;

}
