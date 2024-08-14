package com.opitral.ads.market.api.services;

import java.io.Serializable;
import java.util.List;

import com.opitral.ads.market.api.criteria.Criteria;

public interface IService<E, V extends Serializable> {

    E getById(Integer id);

    List<E> getList(Criteria<E> criteria);

    Criteria<E> parse(String restrict);
    Criteria<E> parse(String restrict, String locale);

    Integer create(V view);

    void postCreate(E entity);

    boolean update(V view);

    void updateEntity(E entity);

    long count(String restrict);

    boolean delete(Integer id);

    long count(Criteria<E> criteria);

}
