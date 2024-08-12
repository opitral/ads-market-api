package com.opitral.ads.market.api.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opitral.ads.market.api.domain.entity.CityEntity_;
import com.opitral.ads.market.api.domain.entity.SubjectEntity_;

public class Fields {
    public static final String ID = "id";

    public static final class Subject {
        public static final String SUBJECT_NAME = "name";

        public static final List<String> LOCALIZED_FIELDS_COLLECTION = new ArrayList<>(Arrays.asList(
                CityEntity_.ID, SUBJECT_NAME
        ));

        public static final List<String> ALL_FIELDS_COLLECTION = new ArrayList<>(Arrays.asList(
                SubjectEntity_.ID, SUBJECT_NAME, SubjectEntity_.NAME_UA, SubjectEntity_.NAME_RU, SubjectEntity_.NAME_EN
        ));

        public static final String ALL_FIELDS_STRING = String.join(",", ALL_FIELDS_COLLECTION);
    }

    public static final class City {
        public static final String CITY_NAME = "name";

        public static final List<String> ALL_FIELDS_COLLECTION = new ArrayList<>(Arrays.asList(
                CityEntity_.ID, CITY_NAME, CityEntity_.NAME_UA, CityEntity_.NAME_RU, CityEntity_.NAME_EN, CityEntity_.SUBJECT
        ));

        public static final String ALL_FIELDS_STRING = String.join(",", ALL_FIELDS_COLLECTION);
    }
}
