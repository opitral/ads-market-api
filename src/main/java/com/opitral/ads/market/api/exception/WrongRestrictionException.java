package com.opitral.ads.market.api.exception;

import java.util.List;
import java.util.Locale;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

public class WrongRestrictionException extends BaseException {
    public WrongRestrictionException(Throwable cause) {
        super("Wrong restriction", cause);
    }

    @Override
    public int getCode() {
        return HttpServletResponse.SC_BAD_REQUEST;
    }

    @Override
    public String formMessage(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage("errors.WrongRestrictionException", null, locale);
    }

    @Override
    public List<String> formListErrors(MessageSource messageSource, Locale locale) {
        return null;
    }
}
