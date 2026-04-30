package com.FYP.Fleet.Dto.Request;

import com.FYP.Fleet.Enums.ExpenseType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExpenseRequestDto {
    @NotNull
    private long tripId;
    @NotNull
    private ExpenseType expenseType;
    private String note;
    @NotNull
    private LocalDate date;
    @NotNull
    private Integer amount;

}
