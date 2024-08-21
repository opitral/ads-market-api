package com.opitral.ads.market.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.opitral.ads.market.api.domain.enums.PostType;

@AllArgsConstructor
@Builder
@Data
public class PublicationResponse {
    private PostType type;
    private String fileId;
    private String text;
    private ButtonResponse button;
}
