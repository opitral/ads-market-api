package com.opitral.ads.market.api.model.response;

import com.opitral.ads.market.api.domain.enums.PublicationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class PublicationResponse {
    private PublicationType type;
    private String fileId;
    private String text;
    private ButtonResponse button;
}
