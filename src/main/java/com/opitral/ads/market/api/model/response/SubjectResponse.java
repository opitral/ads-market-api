package com.opitral.ads.market.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class SubjectResponse {
    private Integer id;
    private String nameUa;
    private String nameRu;
    private String nameEn;
}
