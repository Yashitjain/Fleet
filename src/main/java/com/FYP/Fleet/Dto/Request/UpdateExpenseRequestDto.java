package com.FYP.Fleet.Dto.Request;

import com.FYP.Fleet.Enums.ExpenseType;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateExpenseRequestDto {

    private Long expenseId;
    private Long amount;
    private ExpenseType expenseType;
    private String note;
    private LocalDate date;


}
