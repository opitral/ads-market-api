package com.opitral.ads.market.api.model.view;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.opitral.ads.market.api.common.helpers.GettableById;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserView implements Serializable, GettableById {
    private Integer id;
    private String telegramId;
    private String firstName;
    private String lastName;
}
