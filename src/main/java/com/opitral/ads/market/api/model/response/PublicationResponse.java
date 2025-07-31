package com.opitral.ads.market.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.opitral.ads.market.api.domain.enums.PublicationType;

@AllArgsConstructor
@Builder
@Data
public class PublicationResponse {
    private PublicationType type;
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

    private ButtonResponse button;
    private ButtonResponse button_2;
    private ButtonResponse button_3;
    private ButtonResponse button_4;
    private ButtonResponse button_5;
}
