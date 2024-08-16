package com.opitral.ads.market.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UserResponse {
    private Integer id;
    private String telegramId;
    private String firstName;
    private String lastName;
    private Integer allowedGroupsCount;
}
