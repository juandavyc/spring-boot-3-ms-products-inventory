package com.juandavyc.product.infrastructure.rest.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

// single error dto
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Single error object following JSON API error specification")
public record JsonApiError(
        @Schema(description = "HTTP status code", example = "404")
        String status,

        @Schema(description = "Application-specific error code", example = "PRODUCT_NOT_FOUND")
        String code,

        @Schema(description = "human readable summary", example = "Not Found")
        String title,

        @Schema(description = "Human-readable explanation",
                example = "Product not found with id: 123e4567-e89b-12d3-a456-426614174000")
        String detail,

        @Schema(description = "Meta object containing")
        Map<String, Object> meta,

        @Schema(description = "Source object containing references")
        JsonApiErrorSource source
) {

    // a simple error response
    @Schema(description = "Creates a simple error response")
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

    @Schema(description = "Creates a validation error response with source pointer")
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
