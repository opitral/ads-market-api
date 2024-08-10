package com.opitral.ads.market.api.exception;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import lombok.Getter;

import org.springframework.context.MessageSource;

import com.opencsv.exceptions.CsvException;

@Getter
public class ParsingException extends BaseException{

    private final List<CsvException> capturedExceptions;

    public ParsingException(String msg, List<CsvException> capturedExceptions) {
        super(msg);
        this.capturedExceptions = capturedExceptions;
    }

    @Override
    public String formMessage(MessageSource messageSource, Locale locale){
        return getMessageCode();
    }

    @Override
    public List<String> formListErrors(MessageSource messageSource, Locale locale){
        return capturedExceptions.stream()
                .map(ex -> String.format("Line #%d: [%s]: %s", ex.getLineNumber(), String.join(",", ex.getLine()), ex.getMessage()))
                .collect(Collectors.toList());
    }
}