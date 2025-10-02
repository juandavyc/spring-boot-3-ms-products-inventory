package com.juandavyc.product.infrastructure.rest.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "Product attributes for create and update operations")
public class ProductRequestDto {

    // for fgroups request
    public interface Create {}
    public interface Update {}

    @NotBlank(message = "Name is required", groups = Create.class)
    @Size(max = 100, message = "Name must be less than 100 characters")
    @Schema(
            description = "Product name",
            example = "Laptop Gaming Pro",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 100
    )
    String name;

    @NotNull(message = "Price is required", groups = Create.class)
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0", groups = {Create.class, Update.class})
    @Schema(
            description = "Product price in USD",
            example = "999.99",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "0.0"
    )
    private BigDecimal price;
}