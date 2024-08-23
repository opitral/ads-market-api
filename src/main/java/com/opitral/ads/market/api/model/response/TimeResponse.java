package com.opitral.ads.market.api.model.response;

import com.opitral.ads.market.api.domain.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@AllArgsConstructor
@Builder
@Data
public class TimeResponse {
    private LocalTime time;
    private ScheduleStatus status;
}
