package com.juandavyc.product.infrastructure.rest.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter

public enum ProductErrorMessages {
    NOT_FOUND("404", "PRODUCT_NOT_FOUND", "Product Not Found"),
    ALREADY_EXISTS("409", "PRODUCT_ALREADY_EXISTS", "Product already exists"),
    VALIDATION_ERROR("400", "VALIDATION_ERROR","Validation Failed"),
    INTERNAL_SERVER_ERROR("500", "INTERNAL_SERVER_ERROR","Internal Server Error");

    private final String status;
    private final String code;
    private final String title;
}
