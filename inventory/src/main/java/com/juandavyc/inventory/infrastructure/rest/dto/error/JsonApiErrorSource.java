package com.juandavyc.inventory.infrastructure.rest.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

// the idea is cover all errors:
// "source": { "parameter": "include" },  "source": { "pointer": "" },
@Schema(description = "Source object referencing the source of an error")
public record JsonApiErrorSource(
        @Schema(description = "JSON Pointer to the associated entity in the request document",
                example = "/data/attributes/name")
        String pointer
) {
    public static JsonApiErrorSource pointer(String pointer) {
                return new JsonApiErrorSource(pointer);
    }

//    public static JsonApiErrorSource parameter(String parameter) {
//        return new JsonApiErrorSource(null, parameter);
//    }
}