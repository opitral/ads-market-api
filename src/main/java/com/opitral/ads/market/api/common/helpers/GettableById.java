package com.opitral.ads.market.api.common.helpers;

public interface GettableById {

    Integer getId();

    void setId(Integer id);

    default int compareId(Integer i) {
        if (this.getId() == null && i == null) {
            return 0;
        } else if (this.getId() == null) {
            return -1;
        } else {
            return i == null ? 1 : this.getId().compareTo(i);
        }
    }
}
