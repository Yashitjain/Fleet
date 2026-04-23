package com.FYP.Fleet.Dto.Response;

import lombok.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DriverResponseDto {

    private long id;
    private String name;
    private String phone;
    private Long ownerId;
    private String ownerName;
    private List<Long> tripList;
}
