package com.opitral.ads.market.api.merger;

public interface Merger<E, V> {

    void mergeCreate(E entity, V view);

    void mergeEdit(E entity, V view);
}
