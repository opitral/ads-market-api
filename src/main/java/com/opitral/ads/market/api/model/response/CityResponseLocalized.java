package com.opitral.ads.market.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CityResponseLocalized {
    private Integer id;
    private String name;
    private SubjectResponseLocalized subject;
}
