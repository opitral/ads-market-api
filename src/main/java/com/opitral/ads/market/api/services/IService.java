package com.opitral.ads.market.api.services;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.opitral.ads.market.api.common.response.PageResult;
import com.opitral.ads.market.api.criteria.Criteria;

public interface IService<E, V extends Serializable> {

    E getById(Integer id);

    Map<String, Object> getById(Integer id, Collection<String> fields);
    Map<String, Object> getById(Integer id, Collection<String> fields, String locale);

    List<E> getList(Criteria<E> criteria);

    List<Map<String, Object>> getList(Criteria<E> criteria, Collection<String> fields);
    List<Map<String, Object>> getList(Criteria<E> criteria, Collection<String> fields, String locale);

    List<Map<String, Object>> getList(Collection<String> fields, String restrict);
    List<Map<String, Object>> getList(Collection<String> fields, String restrict, String locale);

    default PageResult<Map<String, Object>> getPage(Collection<String> fields, String restrict) {
        return new PageResult<>(getList(fields, restrict), count(restrict));
    }

    default PageResult<Map<String, Object>> getPage(Collection<String> fields, String restrict, String locale) {
        return new PageResult<>(getList(fields, restrict, locale), count(restrict));
    }

    default PageResult<Map<String, Object>> getPage(Criteria<E> criteria, Collection<String> fields) {
        return new PageResult<>(getList(criteria, fields), count(criteria));
    }

    default PageResult<Map<String, Object>> getPage(Criteria<E> criteria, Collection<String> fields, String locale) {
        return new PageResult<>(getList(criteria, fields, locale), count(criteria));
    }

    Criteria<E> parse(String restrict);
    Criteria<E> parse(String restrict, String locale);

    Integer create(V view);

    void postCreate(E entity);

    boolean update(V view);

    E updateEntity(E entity);

    long count(String restrict);

    boolean delete(Integer id);

    long count(Criteria<E> criteria);

}
