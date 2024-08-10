package com.opitral.ads.market.api.services;

import java.util.List;

public interface IValidator<E> {

    void validForCreate(E entity);

    void validForUpdate(E entity);

    void validForView(E entity);

    void validForView(List<E> entities);

    void validForDelete(E entity);
}