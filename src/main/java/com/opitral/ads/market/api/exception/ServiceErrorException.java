package com.opitral.ads.market.api.exception;

import java.util.List;
import java.util.Locale;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

public class ServiceErrorException extends BaseException {

    private final String message;

    public ServiceErrorException(String message) {
        super(message);
        this.message = message;
    }

    public ServiceErrorException() {
        this("errors.ServiceErrorException");
    }

    @Override
    public int getCode(){
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    @Override
    public String formMessage(MessageSource messageSource, Locale locale) {
        try {
            return messageSource.getMessage(message, null, locale);
        } catch (Exception e) {
            return message;
        }
    }

    @Override
    public List<String> formListErrors(MessageSource messageSource, Locale locale) {
        return null;
    }
}
