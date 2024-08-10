package com.opitral.ads.market.api.exception;

import java.util.List;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

import org.springframework.context.MessageSource;

@Getter
@Setter
public abstract class BaseException extends RuntimeException {
    private int code;
    private String messageCode;

    public BaseException(){
        this("errors.BaseException");
    }

    public BaseException(String message){
        super(message);
        this.messageCode = message;
    }

    public BaseException(String message, Throwable cause){
        super(message, cause);
        this.messageCode = message;
    }

    /**
     * override this method to create custom message for users
     *
     * @return converted message
     */
    public abstract String formMessage(MessageSource messageSource, Locale locale);

    /**
     * override this method to create a list of errors for user
     * <p>
     * return null to show that exception has no list of errors
     *
     * @return list of errors
     */
    public abstract List<String> formListErrors(MessageSource messageSource, Locale locale);
}
