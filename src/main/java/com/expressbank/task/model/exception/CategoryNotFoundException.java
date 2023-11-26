package com.expressbank.task.model.exception;

import com.expressbank.task.model.enums.ApplicationMessages;
import lombok.Getter;

@Getter
public class CategoryNotFoundException extends RuntimeException {

    String code;

    public CategoryNotFoundException(ApplicationMessages message) {
        super(message.getMessage());
        this.code = message.getCode();
    }
}
