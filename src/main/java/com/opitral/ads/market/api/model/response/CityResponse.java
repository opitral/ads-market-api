package com.opitral.ads.market.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CityResponse {
    private Integer id;
    private String nameUa;
    private String nameRu;
    private String nameEn;
    private SubjectResponse subject;
}
