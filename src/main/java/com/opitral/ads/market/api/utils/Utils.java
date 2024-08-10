package com.opitral.ads.market.api.utils;

import java.util.function.Supplier;

public class Utils {
    public static <T> T getLocalizedValue(Supplier<T> ua, Supplier<T> ru, Supplier<T> en, String locale) {
        if (locale.equals("ru")) {
            return ru.get();

        } else if (locale.equals("en")) {
            return en.get();

        } else {
            return ua.get();
        }
    }
}
