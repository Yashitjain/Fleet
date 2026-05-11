package com.FYP.Fleet.Dto.Response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerBalanceDto {
    private Long ownerId;
    private String ownerName;
    private String ownerPhone;
    private Long totalPay;
    private Long totalAdvance;
    private Long amountToPay;
    private String status; // PAYABLE, RECEIVABLE, SETTLED
    private List<OwnerBalanceTripSummaryResponseDto> trips;
}