package com.opitral.ads.market.api.domain.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Price {

    @NotNull(message = "error.price.with.pin.empty")
    @Min(value = 1, message = "error.price.with.pin.min")
    @Max(value = 10000, message = "error.price.with.pin.max")
    private Integer withPin;

    @NotNull(message = "error.price.without.pin.empty")
    @Min(value = 1, message = "error.price.without.pin.min")
    @Max(value = 10000, message = "error.price.without.pin.max")
    private Integer withoutPin;

}