package com.expressbank.task.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationMessages {

    CATEGORY_ALREADY_EXIST("CATEGORY_ALREADY_EXIST", "Category is already exist"),
    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", "Category not found"),
    SUPPLIER_ALREADY_EXIST("SUPPLIER_ALREADY_EXIST", "Supplier is already exist"),
    SUPPLIER_NOT_FOUND("SUPPLIER_NOT_FOUND", "Supplier not found"),
    PRODUCT_ALREADY_EXIST("PRODUCT_ALREADY_EXIST", "Product is already exist"),
    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND", "Product not found"),
    UNEXPECTED_EXCEPTION("UNEXPECTED_EXCEPTION", "Unexpected exception occurred"),
    BAD_INPUT("BAD_INPUT", "Request information are incorrect");

    private final String code;
    private final String message;
}
