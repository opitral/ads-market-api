package com.opitral.ads.market.api.converters;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

public abstract class LocalizedConverter<T> extends Converter<T> {
    @Override
    public Map<String, Object> convert(T object, Collection<String> fields, String locale) {
        throw new NotImplementedException();
    }

    @Override
    public Map<String, Object> convert(T object, Collection<String> fields) {
        return convert(object, fields, "ua");
    }
}
