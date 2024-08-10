package com.opitral.ads.market.api.exception;

import java.util.List;
import java.util.Locale;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

public class ForbiddenException extends BaseException {

    @Override
    public int getCode(){
        return HttpServletResponse.SC_FORBIDDEN;
    }

    @Override
    public String formMessage(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage("errors.ForbiddenException", null, locale);
    }

    @Override
    public List<String> formListErrors(MessageSource messageSource, Locale locale) {
        return null;
    }
}
