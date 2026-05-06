package com.FYP.Fleet.Dto.Response;

import com.FYP.Fleet.Enums.Status;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TripStatusResponseDto {
    private long tripId;
    private Status status;
}
