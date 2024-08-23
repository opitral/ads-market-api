package com.opitral.ads.market.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Price {

    @NotNull(message = "Цена с закрепом не может быть пустой")
    @Min(value = 1, message = "Минимальная цена - 1")
    @Max(value = 10000, message = "Максимальная цена - 10000")
    private Integer withPin;

    @NotNull(message = "Цена без закрепа не может быть пустой")
    @Min(value = 1, message = "Минимальная цена - 1")
    @Max(value = 10000, message = "Максимальная цена - 10000")
    private Integer withoutPin;

}