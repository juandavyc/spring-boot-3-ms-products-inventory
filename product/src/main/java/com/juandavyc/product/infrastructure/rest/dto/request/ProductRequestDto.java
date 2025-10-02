package com.juandavyc.product.infrastructure.rest.dto.request;

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
public class ProductRequestDto {

    public interface Create {}
    public interface Update {}

    @NotBlank(message = "Name is required", groups = Create.class)
    @Size(max = 100, message = "Name must be less than 100 characters")
    String name;

    @NotNull(message = "Price is required", groups = Create.class)
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0", groups = {Create.class, Update.class})
    private BigDecimal price;
}
