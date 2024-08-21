package com.opitral.ads.market.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.opitral.ads.market.api.domain.enums.PostType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Publication {

    @NotNull(message = "error.post.type.empty")
    @Enumerated(EnumType.STRING)
    private PostType type;

    @Size(max = 255, message = "error.post.file.id.size")
    private String fileId;

    @Size(max = 500, message = "error.post.text.size")
    private String text;

    @Valid
    private Button button;

}
