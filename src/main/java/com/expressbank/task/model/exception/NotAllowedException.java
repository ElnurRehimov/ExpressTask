package com.expressbank.task.model.exception;

import lombok.Getter;

@Getter
public class NotAllowedException extends RuntimeException {

    String code;

    public NotAllowedException(String message, String code) {
        super(message);
        this.code = code;
    }

}
