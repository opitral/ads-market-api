package com.opitral.ads.market.api.model.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.opitral.ads.market.api.domain.enums.PostStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private Integer id;
    private PublicationResponse publication;
    private Integer groupId;
    private String groupTelegramId;

    private Boolean withPin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publishDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime publishTime;

    private PostStatus status;

    private Integer messageId;
}
