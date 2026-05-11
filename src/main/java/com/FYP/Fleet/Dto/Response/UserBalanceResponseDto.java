package com.FYP.Fleet.Dto.Response;

import com.FYP.Fleet.Dto.MiniResponseDto.MiniTripResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBalanceResponseDto {
    private Integer totalTrips;
    private Integer totalActiveTrips;
    private Long totalFreightEarned;
    private Long bookedProfit;
    private Long estimatedProfit;
//    private List<MiniTripResponseDto> trips;
}
