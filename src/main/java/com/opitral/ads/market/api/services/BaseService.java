package com.opitral.ads.market.api.services;

import java.io.Serializable;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opitral.ads.market.api.common.helpers.GettableById;
import com.opitral.ads.market.api.merger.Merger;
import com.opitral.ads.market.api.criteria.Criteria;
import com.opitral.ads.market.api.repositories.BaseRepository;
import com.opitral.ads.market.api.repositories.criteria.ICriteriaRepository;
import com.opitral.ads.market.api.exception.NoSuchEntityException;
import com.opitral.ads.market.api.exception.ValidationException;
import com.opitral.ads.market.api.exception.ServiceErrorException;

/**
 * @param <E> Entity class
 * @param <V> View class
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public abstract class BaseService<E extends GettableById, V extends GettableById & Serializable> implements IService<E, V> {

    @Autowired
    protected BaseRepository<E> repository;

    @Autowired
    protected Merger<E, V> merger;

    @Autowired
    protected ICriteriaRepository criteriaRepository;

    @Autowired
    protected IValidator<E> validationService;

    protected final Class<E> persistentClass;
    protected final Supplier<E> newEntitySupplier;

    public BaseService(Class<E> entityClass, Supplier<E> newEntitySupplier) {
        this.persistentClass = entityClass;
        this.newEntitySupplier = newEntitySupplier;
    }

    @Override
    @Transactional
    public E getById(Integer id) {
        E entity = repository.findById(id).orElseThrow(() -> new NoSuchEntityException(persistentClass.getName(), "id: " + id));
        validationService.validForView(entity);
        return entity;
    }

    @Override
    @Transactional
    public List<E> getList(Criteria<E> criteria) {
        List<E> entities = criteriaRepository.find(criteria);
        validationService.validForView(entities);
        return entities;
    }

    @Override
    public Criteria<E> parse(String restrict, String locale) {
        return parse(restrict);
    }

    @Override
    @Transactional
    public Integer create(V view) {
        E entity = newEntitySupplier.get();
        merger.mergeCreate(entity, view);
        postCreate(entity);
        validationService.validForCreate(entity);
        entity = repository.saveAndFlush(entity);
        return entity.getId();
    }

    public void postCreate(E entity) {}

    public void postUpdate(E entity) {}

    @Override
    @Transactional
    public boolean update(V view) {
        if (view.getId() == null) {
            throw new ValidationException(persistentClass.getName(), "errors.EntityCreateException.id.update");
        }
        E entity = repository.findById(view.getId())
                .orElseThrow(() -> new NoSuchEntityException(persistentClass.getName(), "id" + view.getId()));
        merger.mergeEdit(entity, view);
        postUpdate(entity);
        validationService.validForUpdate(entity);
        updateEntity(entity);
        return true;
    }

    @Override
    @Transactional
    public void updateEntity(E entity) {
        repository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public long count(String restrict) {
        return count(parse(restrict));
    }

    @Override
    @Transactional
    public long count(Criteria<E> criteria) {
        return criteriaRepository.count(criteria);
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        throw new ServiceErrorException("We can't delete entity " + persistentClass.getName());
    }
}