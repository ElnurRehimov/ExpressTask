package com.expressbank.task.model.exception;

import lombok.Getter;

@Getter
public class ProductAlreadyExistException extends RuntimeException {

    String code;

    public ProductAlreadyExistException(String message, String code) {
        super(message);
        this.code = code;
    }

}
