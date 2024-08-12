package com.opitral.ads.market.api.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CityListResponseLocalized {
    private Long total;
    private List<CityResponseLocalized> responseList;
}
