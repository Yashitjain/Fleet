package com.FYP.Fleet.Dto.Response;

import com.FYP.Fleet.Enums.ExpenseType;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExpenseResponseDto {
    private long expenseId;
    private long tripId;
    private long driverId;
    private long ownerId;
    private String ownerName;
    private int amount;
    private LocalDate date;
    private String source;
    private String destination;
    private String note;
    private ExpenseType expenseType;
}
