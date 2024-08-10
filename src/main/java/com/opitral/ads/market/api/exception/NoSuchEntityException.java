package com.opitral.ads.market.api.exception;

import java.util.List;
import java.util.Locale;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

public class NoSuchEntityException extends BaseException {
    private static final String DEFAULT_MESSAGE = "No entity of type '%s' and params %s";
    private static final String NO_ENTITY_OF_TYPE = "No entity of type '%s'";

    public NoSuchEntityException(String className){
        super(String.format(NO_ENTITY_OF_TYPE, processClassName(className)));
    }

    public NoSuchEntityException(String className, String params){
        super(String.format(DEFAULT_MESSAGE, processClassName(className), params));
    }

    private static String processClassName(String className) {
        int dot = className.lastIndexOf('.');
        if (dot > -1) {
            className = className.substring(dot + 1);
        }

        return className.toLowerCase().replace("entity", "");
    }

    public int getCode(){
        return HttpServletResponse.SC_NOT_FOUND;
    }

    @Override
    public String formMessage(MessageSource messageSource, Locale locale) {
        return getMessage();
    }

    @Override
    public List<String> formListErrors(MessageSource messageSource, Locale locale) {
        return null;
    }
}
