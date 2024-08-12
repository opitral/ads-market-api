package com.opitral.ads.market.api.common.helpers;

public interface GettableById {

    Integer getId();

    void setId(Integer id);

    default int compareId(int i) {
        if (this.getId() == null) {
            return 0;
        } else {
            this.getId();
            return this.getId() != null ? this.getId().compareTo(i) : -1;
        }
    }
}
