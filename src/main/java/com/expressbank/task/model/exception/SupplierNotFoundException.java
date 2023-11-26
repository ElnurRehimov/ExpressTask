package com.expressbank.task.model.exception;

import com.expressbank.task.model.enums.ApplicationMessages;
import lombok.Getter;

@Getter
public class SupplierNotFoundException extends RuntimeException {

    String code;

    public SupplierNotFoundException(ApplicationMessages message) {
        super(message.getMessage());
        this.code = message.getCode();
    }
}
