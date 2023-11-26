package com.expressbank.task.model.exception.handler;

import com.expressbank.task.model.dto.response.ErrorResponse;
import com.expressbank.task.model.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

import static com.expressbank.task.model.enums.ApplicationMessages.BAD_INPUT;
import static com.expressbank.task.model.enums.ApplicationMessages.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex) {

        log.error("Exception: {}", ExceptionUtils.getStackTrace(ex));
        return ErrorResponse.of(UNEXPECTED_EXCEPTION.getCode(), UNEXPECTED_EXCEPTION.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(CategoryNotFoundException ex) {

        log.error("CategoryNotFoundException: {}", ExceptionUtils.getStackTrace(ex));
        return ErrorResponse.of(ex.getCode(), ex.getMessage());
    }


    @ExceptionHandler(SupplierNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(SupplierNotFoundException ex) {

        log.error("SupplierNotFoundException: {}", ExceptionUtils.getStackTrace(ex));
        return ErrorResponse.of(ex.getCode(), ex.getMessage());
    }


    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(ProductNotFoundException ex) {

        log.error("ProductNotFoundException: {}", ExceptionUtils.getStackTrace(ex));
        return ErrorResponse.of(ex.getCode(), ex.getMessage());
    }


    @ExceptionHandler(CategoryAlreadyExistException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(CategoryAlreadyExistException ex) {

        log.error("CategoryAlreadyExistException {}", ExceptionUtils.getStackTrace(ex));
        return ErrorResponse.of(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(SupplierAlreadyExistException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(SupplierAlreadyExistException ex) {

        log.error("SupplierAlreadyExistException {}", ExceptionUtils.getStackTrace(ex));
        return ErrorResponse.of(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(ProductAlreadyExistException ex) {

        log.error("ProductAlreadyExistException {}", ExceptionUtils.getStackTrace(ex));
        return ErrorResponse.of(ex.getCode(), ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(MethodArgumentNotValidException ex) {

        var logMessage = ex.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .reduce((s1, s2) -> String.format("%s, %s", s1, s2))
                .orElse("");

        log.error("MethodArgumentNotValidException: {}", logMessage);
        return ErrorResponse.of(BAD_INPUT.getCode(), ex.getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(MissingServletRequestParameterException ex) {

        log.error("MissingServletRequestParameterException: {}", ExceptionUtils.getStackTrace(ex));
        return ErrorResponse.of(BAD_INPUT.getCode(), ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public ErrorResponse handle(HttpRequestMethodNotSupportedException ex) {

        log.error("HttpRequestMethodNotSupportedException: {}", ExceptionUtils.getStackTrace(ex));
        return ErrorResponse.of(BAD_INPUT.getCode(), ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(HttpMessageNotReadableException ex) {

        log.error("HttpMessageNotReadableException: {}", ExceptionUtils.getStackTrace(ex));
        return ErrorResponse.of(BAD_INPUT.getCode(), Objects.requireNonNull(ex.getMessage()).split(":")[0]);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(MethodArgumentTypeMismatchException ex) {

        log.error("MethodArgumentTypeMismatchException: {}", ExceptionUtils.getStackTrace(ex));
        return ErrorResponse.of(BAD_INPUT.getCode(), Objects.requireNonNull(ex.getMessage()).split(";")[0]);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(MissingRequestHeaderException ex) {

        log.error("MethodArgumentTypeMismatchException: {}", ExceptionUtils.getStackTrace(ex));
        return ErrorResponse.of(BAD_INPUT.getCode(), Objects.requireNonNull(ex.getMessage()).split(";")[0]);
    }
}
