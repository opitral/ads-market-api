package com.opitral.ads.market.api.model.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.opitral.ads.market.api.domain.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.opitral.ads.market.api.common.helpers.GettableById;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostView implements Serializable, GettableById {
    private Integer id;
    private PublicationView publication;
    private Integer groupId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publishDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime publishTime;

    private PostStatus status;

    private Integer messageId;
}
