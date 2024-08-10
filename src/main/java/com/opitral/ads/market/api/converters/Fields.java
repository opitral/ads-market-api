package com.opitral.ads.market.api.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fields {
    public static final String ID = "id";

    public static final class Subject {
        public static final String SUBJECT_ID = "subject_id";
        public static final String SUBJECT_NAME = "subject_name";
        public static final String SUBJECT_NAME_UA = "subject_name_ua";
        public static final String SUBJECT_NAME_RU = "subject_name_ru";
        public static final String SUBJECT_NAME_EN = "subject_name_en";

        public static final List<String> ALL_FIELDS_COLLECTION = new ArrayList<>(Arrays.asList(
                SUBJECT_ID, SUBJECT_NAME, SUBJECT_NAME_UA, SUBJECT_NAME_RU, SUBJECT_NAME_EN
        ));

        public static final String ALL_FIELDS_STRING = String.join(",", ALL_FIELDS_COLLECTION);
    }
}
