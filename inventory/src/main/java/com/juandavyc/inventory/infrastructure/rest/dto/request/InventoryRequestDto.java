package com.juandavyc.inventory.infrastructure.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "Inventory attributes for create and update operations")
public class InventoryRequestDto {

    @NotNull(message = "The quantity is required.")
    @Min(value = 0, message = "Quantity cannot be negative.")
    private Integer quantity;


}