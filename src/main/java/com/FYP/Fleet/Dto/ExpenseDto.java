package com.FYP.Fleet.Dto;

import com.FYP.Fleet.Enums.ExpenseType;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExpenseDto {
    private long tripId;
    private ExpenseType expenseType;
    private String note;
    private LocalDate date;
    private Integer amount;

}
