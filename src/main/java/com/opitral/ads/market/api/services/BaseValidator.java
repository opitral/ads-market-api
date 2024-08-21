package com.opitral.ads.market.api.services;

import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.opitral.ads.market.api.common.helpers.GettableById;
import com.opitral.ads.market.api.exception.ValidationException;

public abstract class BaseValidator<E extends GettableById> implements IValidator<E> {

    @Autowired
    protected Validator validator;

    private final Class<E> persistentClass;

    public BaseValidator(Class<E> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Override
    public void validForCreate(E entity) {
        if (entity.compareId(0) > 0) {
            throw new ValidationException(persistentClass.getName(), "Мы не может создать сущность с id");
        }

        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        if (violations != null && !violations.isEmpty()) {
            throw new ValidationException(persistentClass.getName(), violations);
        }
    }

    @Override
    public void validForUpdate(E entity) {
        if (entity.compareId(0) == 0) {
            throw new ValidationException(persistentClass.getName(), "Мы не может обновить сущность без id");
        }
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        if (violations != null && !violations.isEmpty()) {
            throw new ValidationException(persistentClass.getName(), violations);
        }
    }

    @Override
    public void validForView(List<E> entities) {
        for (E entity : entities) {
            validForView(entity);
        }
    }

    @Override
    public void validForView(E entity) {}

    @Override
    public void validForDelete(E entity) {}
}