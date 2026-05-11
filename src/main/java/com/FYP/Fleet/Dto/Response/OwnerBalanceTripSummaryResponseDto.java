package com.FYP.Fleet.Dto.Response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OwnerBalanceTripSummaryResponseDto {

    private Long tripId;
    private String source;
    private String destination;
    private Long rate;
    private Boolean settled;
    private String vehicleNumber;
    private Long advance;
}
