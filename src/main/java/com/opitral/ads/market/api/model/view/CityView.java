package com.opitral.ads.market.api.model.view;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.opitral.ads.market.api.common.helpers.GettableById;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityView implements Serializable, GettableById {
    private Integer id;
    private String name;
    private Integer subjectId;
}
