package com.FYP.Fleet.Dto.MiniResponseDto;

import com.FYP.Fleet.Enums.ExpenseType;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MiniExpenseResponseDto {
    private long expenseId;
    private int amount;
    private LocalDate date;
    private String note;
    private ExpenseType expenseType;
}
