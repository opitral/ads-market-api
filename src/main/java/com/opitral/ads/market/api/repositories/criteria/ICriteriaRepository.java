package com.opitral.ads.market.api.repositories.criteria;

import java.util.List;

import com.opitral.ads.market.api.criteria.Criteria;

public interface ICriteriaRepository{
    <T> List<T> find(Criteria<T> criteria);
    <T> long count(Criteria<T> criteria);
}
