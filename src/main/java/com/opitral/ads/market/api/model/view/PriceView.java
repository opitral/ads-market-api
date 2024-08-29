package com.opitral.ads.market.api.model.view;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceView {
    private Integer withoutPin;
    private Integer withPin;
}