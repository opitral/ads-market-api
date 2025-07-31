package com.opitral.ads.market.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.opitral.ads.market.api.domain.enums.PublicationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Publication {

    @NotNull(message = "Тип поста не может быть пустым")
    @Enumerated(EnumType.STRING)
    private PublicationType type;

    @Size(max = 4096, message = "Текст поста должен содержать не более 4096 символов")
    private String text;

    private String fileId;
    private String fileId_2;
    private String fileId_3;
    private String fileId_4;
    private String fileId_5;
    private String fileId_6;
    private String fileId_7;
    private String fileId_8;
    private String fileId_9;
    private String fileId_10;

    private Button button;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
}
