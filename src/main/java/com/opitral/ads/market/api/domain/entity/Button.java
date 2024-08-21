package com.opitral.ads.market.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Button {

    @Size(max = 50, message = "error.post.button.name.size")
    private String name;

    @Size(max = 255, message = "error.post.button.url.size")
    private String url;

}
