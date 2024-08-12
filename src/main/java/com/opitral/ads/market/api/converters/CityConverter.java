package com.opitral.ads.market.api.converters;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.opitral.ads.market.api.domain.entity.CityEntity;
import com.opitral.ads.market.api.domain.entity.CityEntity_;
import com.opitral.ads.market.api.utils.Utils;
import static com.opitral.ads.market.api.converters.Fields.City.*;

@Component
@RequiredArgsConstructor
public class CityConverter extends LocalizedConverter<CityEntity> {

    private final SubjectConverter subjectConverter;

    @Override
    public Map<String, Object> convert(CityEntity object, Collection<String> fields, String locale) {
        Map<String, Object> res = new HashMap<>();

        if (fields.contains(CityEntity_.ID))
            res.put(CityEntity_.ID, object.getId());

        if (fields.contains(CITY_NAME))
            res.put(CITY_NAME, Utils.getLocalizedValue(object::getNameUa, object::getNameRu, object::getNameEn, locale));

        if (fields.contains(CityEntity_.NAME_UA))
            res.put(CityEntity_.NAME_UA, object.getNameUa());

        if (fields.contains(CityEntity_.NAME_RU))
            res.put(CityEntity_.NAME_RU, object.getNameRu());

        if (fields.contains(CityEntity_.NAME_EN))
            res.put(CityEntity_.NAME_EN, object.getNameEn());

        if (fields.contains(CityEntity_.SUBJECT_ID))
            res.put(CityEntity_.SUBJECT_ID, object.getSubject().getId());

        if (fields.contains(CityEntity_.SUBJECT))
            if (object.getSubjectId() != null)
                res.put(CityEntity_.SUBJECT, subjectConverter.convert(object.getSubject(), Fields.Subject.LOCALIZED_FIELDS_COLLECTION, locale));
            else
                res.put(CityEntity_.SUBJECT, null);

            return res;
    }

    @Override
    public void checkFieldsWithPermissionsToView(CityEntity object, Collection<String> fields, Map<String, Object> res) {}
}
