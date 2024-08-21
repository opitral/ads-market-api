package com.opitral.ads.market.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Button {

    @Size(max = 50, message = "Текст кнопки должен содержать не более 50 символов")
    private String name;

    @Size(max = 255, message = "Ссылка кнопки дожна содержать не более 255 символов")
    private String url;

}
