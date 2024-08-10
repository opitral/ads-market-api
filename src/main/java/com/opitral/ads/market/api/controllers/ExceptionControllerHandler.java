package com.opitral.ads.market.api.controllers;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opitral.ads.market.api.exception.BaseException;
import com.opitral.ads.market.api.common.response.Response;
import com.opitral.ads.market.api.common.response.ResponseError;
import com.opitral.ads.market.api.exception.ParsingException;
import com.opitral.ads.market.api.utils.FixedLoggerNames;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerHandler {

    private static final Logger errorLogger = LoggerFactory.getLogger(FixedLoggerNames.MAIN_ERROR_LOGGER);
    private final MessageSource messageSource;

    @ExceptionHandler(BaseException.class)
    public Response<?> handle(BaseException e) {
        ResponseError error = new ResponseError(e.getCode(), e.formMessage(messageSource, getLocale()), e.formListErrors(messageSource, getLocale()));
        return Response.of(error);
    }

    @ExceptionHandler(BindException.class)
    public Response<?> handle(BindException e) {
        return Response.of(new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    public Response<?> handle(ConversionNotSupportedException e) {
        return Response.of(new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public Response<?> handle(HttpMediaTypeNotAcceptableException e) {
        return Response.of(new ResponseError(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Response<?> handle(HttpMediaTypeNotSupportedException e) {
        return Response.of(new ResponseError(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response<?> handle(HttpMessageNotReadableException e) {
        return Response.of(new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public Response<?> handle(HttpMessageNotWritableException e) {
        return Response.of(new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<?> handle(HttpRequestMethodNotSupportedException e) {
        return Response.of(new ResponseError(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> handle(MethodArgumentNotValidException e) {
        return Response.of(new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response<?> handle(MissingServletRequestParameterException e) {
        return Response.of(new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), List.of("Missing request parameter: " + e.getParameterName())));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public Response<?> handle(MissingServletRequestPartException e) {
        return Response.of(new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), List.of("Missing required part: " + e.getRequestPartName())));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Response<?> handle(NoResourceFoundException e) {
        return Response.of(new ResponseError(HttpStatus.NOT_FOUND.value(), "The path " + e.getResourcePath() + " was not found"));
    }

    @ExceptionHandler(TypeMismatchException.class)
    public Response<?> handle(TypeMismatchException e) {
        return Response.of(new ResponseError(HttpStatus.BAD_REQUEST.value(), "Invalid type for parameter: " + e.getPropertyName()));
    }

    @ExceptionHandler(Exception.class)
    public Response<?> handle(Exception e) {
        log.error("Internal error", e);
        errorLogger.error("Internal error", e);
        return Response.of(new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal error"));
    }

    @ExceptionHandler(ParsingException.class)
    public Response<?> handle(ParsingException e) {
        log.error("Parsing error", e);
        return Response.of(new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage(), e.formListErrors(messageSource, getLocale())));
    }
}