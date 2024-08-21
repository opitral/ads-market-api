package com.opitral.ads.market.api.model.view;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.opitral.ads.market.api.domain.enums.PostType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationView implements Serializable {
    private PostType type;
    private String fileId;
    private String text;
    private ButtonView button;
}
