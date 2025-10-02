package com.juandavyc.product.infrastructure.rest.dto.error;

public record JsonApiErrorSource(
        String pointer
) {
    public static JsonApiErrorSource pointer(String pointer) {
        return new JsonApiErrorSource(pointer);
    }

//    public static JsonApiErrorSource parameter(String parameter) {
//        return new JsonApiErrorSource(null, parameter);
//    }
}