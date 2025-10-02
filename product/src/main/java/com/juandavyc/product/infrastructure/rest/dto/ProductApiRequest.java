package com.juandavyc.product.infrastructure.rest.dto;

import com.juandavyc.product.infrastructure.rest.dto.request.ProductRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductApiRequest{
    @Valid
    private Data data;

    @Setter
    @Getter
    public static class Data {
        private String type;

        @Valid
        @NotNull(message = "Attributes are required")
        private ProductRequestDto attributes;
    }

}
