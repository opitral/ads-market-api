package com.opitral.ads.market.api.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.opitral.ads.market.api.common.helpers.GettableById;

public abstract class Converter<T> {

    public Map<String, Object> convert(T object, Collection<String> fields, String locale) {
        return convert(object, fields);
    }

    public abstract Map<String, Object> convert(T object, Collection<String> fields);

    public List<Map<String, Object>> convert(Collection<T> objects, Collection<String> fields, String locale) {
        List<Map<String, Object>> result = new ArrayList<>();
        for(T t : objects)
            result.add(convert(t, fields, locale));

        return result;
    }

    public List<Map<String, Object>> convert(Collection<T> objects, Collection<String> fields){
        List<Map<String, Object>> result = new ArrayList<>();
        for(T t : objects)
            result.add(convert(t, fields));

        return result;
    }

    public void convertMainFields(T object, Collection<String> fields, Map<String,Object> res){
        checkFieldsWithPermissionsToView(object,fields,res);
        if (object instanceof GettableById)
            convertId(res,(GettableById)object,fields);
    }

    public void convertId(Map<String,Object> res, GettableById object, Collection<String> fields){
        if(fields.contains(Fields.ID))
            res.put(Fields.ID, object.getId());
    }

    public abstract void checkFieldsWithPermissionsToView(T object, Collection<String> fields, Map<String,Object> res);
}
