package com.juandavyc.product.infrastructure.rest.dto.error;

import java.util.List;


// multiples errors dto
public record JsonApiErrorResponse(
        List<JsonApiError> errors
) {
    public static JsonApiErrorResponse of(JsonApiError error) {
        return new JsonApiErrorResponse(List.of(error));
    }

    public static JsonApiErrorResponse of(List<JsonApiError> errors) {
        return new JsonApiErrorResponse(errors);
    }
}
