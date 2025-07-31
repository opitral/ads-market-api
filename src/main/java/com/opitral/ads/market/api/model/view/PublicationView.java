package com.opitral.ads.market.api.model.view;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.opitral.ads.market.api.domain.enums.PublicationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationView implements Serializable {
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

    private ButtonView button;
    private ButtonView button_2;
    private ButtonView button_3;
    private ButtonView button_4;
    private ButtonView button_5;
}
