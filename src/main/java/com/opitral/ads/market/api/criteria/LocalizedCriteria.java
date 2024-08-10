package com.opitral.ads.market.api.criteria;

import java.util.Set;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.StringUtils;

public abstract class LocalizedCriteria<T> extends Criteria<T> {
    @JsonIgnore
    private String locale;

    public LocalizedCriteria(Class<T> entityClass, String locale) {
        super(entityClass);

        this.locale = locale;
    }

    public LocalizedCriteria(Class<T> entityClass) {
        this(entityClass, "ua");
    }

    @Override
    protected Path<?> getAttributePath(Root<T> root, String attributeName) {
        if (getLocalizedFields().contains(attributeName))
            return root.get(getLocalizedAttributeName(attributeName));

        return root.get(attributeName);
    }

    protected String getLocalizedAttributeName(String attributeName) {
        return attributeName + StringUtils.capitalize(locale);
    }

    protected abstract Set<String> getLocalizedFields();
}
