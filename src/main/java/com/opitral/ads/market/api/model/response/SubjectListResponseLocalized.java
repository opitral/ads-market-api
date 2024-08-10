package com.opitral.ads.market.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class SubjectListResponseLocalized {
    private Long total;
    private List<SubjectResponseLocalized> responseList;
}
