package com.juandavyc.product.infrastructure.rest.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductApiRequest<T>{

    private Data<T> data;

    @Setter
    @Getter
    public static class Data<T> {
        private String type;
        private T attributes;
    }

}
