package com.opitral.ads.market.api.settings;

import java.util.List;
import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

public class SmartLocaleResolver extends AcceptHeaderLocaleResolver {

    private static final List<Locale> LOCALES = List.of(new Locale("ua"), new Locale("ru"), new Locale("en"), new Locale("uk"));

    public String resolveLocaleLanguage(HttpServletRequest request) {
        Locale locale = resolveLocale(request);
        String language = locale.getLanguage();

        if (language.equals("uk")) {
            return "ua";

        } else {
            return language;
        }
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lang;
        if (request.getHeader("Accept-Language") == null || "".equals(request.getHeader("Accept-Language")))
            lang = "uk_UA";

        else
            lang = request.getHeader("Accept-Language");

        try {
            if(lang != null && lang.contains("_")){
                lang = lang.split("_")[0];
            }

            List<Locale.LanguageRange> list = Locale.LanguageRange.parse(lang);
            Locale locale = Locale.lookup(list, LOCALES);

            if (locale == null)
                return Locale.getDefault();

            return locale;

        } catch (IllegalArgumentException ex){
            return Locale.getDefault();
        }
    }
}