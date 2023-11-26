package com.expressbank.task.model.exception;

import com.expressbank.task.model.enums.ApplicationMessages;
import lombok.Getter;

@Getter
public class CategoryAlreadyExistException extends RuntimeException {

    String code;

    public CategoryAlreadyExistException(ApplicationMessages message) {
        super(message.getMessage());
        this.code = message.getCode();
    }
}
