package com.opitral.ads.market.api.common.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private List<T> items;
    private long total;

    @Override
    public String toString() {
        return "PageResult(items=" + this.getItems() + ", total=" + this.getTotal() + ")";
    }
}
