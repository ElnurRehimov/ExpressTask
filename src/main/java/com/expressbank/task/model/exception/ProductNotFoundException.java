package com.expressbank.task.model.exception;

import com.expressbank.task.model.enums.ApplicationMessages;
import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException {

    String code;

    public ProductNotFoundException(ApplicationMessages message) {
        super(message.getMessage());
        this.code = message.getCode();
    }
}
