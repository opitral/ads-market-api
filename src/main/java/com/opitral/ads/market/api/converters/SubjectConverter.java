package com.opitral.ads.market.api.converters;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.opitral.ads.market.api.domain.entity.SubjectEntity;
import com.opitral.ads.market.api.domain.entity.SubjectEntity_;
import com.opitral.ads.market.api.utils.Utils;
import static com.opitral.ads.market.api.converters.Fields.Subject.*;

@Component
@RequiredArgsConstructor
public class SubjectConverter extends LocalizedConverter<SubjectEntity> {
    @Override
    public Map<String, Object> convert(SubjectEntity object, Collection<String> fields, String locale) {
        Map<String, Object> res = new HashMap<>();

        if (fields.contains(SubjectEntity_.ID))
            res.put(SubjectEntity_.ID, object.getId());

        if (fields.contains(SUBJECT_NAME))
            res.put(SUBJECT_NAME, Utils.getLocalizedValue(object::getNameUa, object::getNameRu, object::getNameEn, locale));

        if (fields.contains(SubjectEntity_.NAME_UA))
            res.put(SubjectEntity_.NAME_UA, object.getNameUa());

        if (fields.contains(SubjectEntity_.NAME_RU))
            res.put(SubjectEntity_.NAME_RU, object.getNameRu());

        if (fields.contains(SubjectEntity_.NAME_EN))
            res.put(SubjectEntity_.NAME_EN, object.getNameEn());

        return res;
    }

    @Override
    public void checkFieldsWithPermissionsToView(SubjectEntity object, Collection<String> fields, Map<String, Object> res) {}
}
