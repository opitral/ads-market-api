package com.opitral.ads.market.api.domain.entity;

import com.opitral.ads.market.api.domain.enums.PublicationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Publication {

    @NotNull(message = "Тип поста не может быть пустым")
    @Enumerated(EnumType.STRING)
    private PublicationType type;

    @Size(max = 255, message = "Идентификор файла доджен содержать не более 255 символов")
    private String fileId;

    @Size(max = 500, message = "Текст поста должен содержать не более 500 символов")
    private String text;

    @Valid
    private Button button;

}
