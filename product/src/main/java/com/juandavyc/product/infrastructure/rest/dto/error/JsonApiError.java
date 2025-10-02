package com.juandavyc.product.infrastructure.rest.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.juandavyc.product.infrastructure.rest.advice.ProductErrorMessages;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record JsonApiError(
        String status,
        String code,
        String title,
        String detail,
        Map<String, Object> meta,
        JsonApiErrorSource source
) {

    public static JsonApiError error(String status, String title, String detail) {
        return new JsonApiError(
                status,
                null,
                title,
                detail,
                null,
                null
        );
    }

    public static JsonApiError errors(String detail, Map<String, Object> meta, JsonApiErrorSource source) {
        return new JsonApiError("400",
                "VALIDATION_ERROR",
                "Validation Failed",
                detail,
                meta,
                source
        );
    }
}

