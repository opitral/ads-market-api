package com.opitral.ads.market.api.repositories;

import jakarta.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class BaseRepositoryImpl<E> extends SimpleJpaRepository<E, Integer> implements BaseRepository<E> {
    public BaseRepositoryImpl(JpaEntityInformation<E, Integer> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }
}
