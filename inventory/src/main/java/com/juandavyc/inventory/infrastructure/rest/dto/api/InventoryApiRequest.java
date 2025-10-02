package com.juandavyc.inventory.infrastructure.rest.dto.api;

import com.juandavyc.inventory.infrastructure.rest.dto.request.InventoryRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "JSON API request wrapper for product operations")
public class InventoryApiRequest {
    @Valid
    @Schema(description = "Main data object containing product information")
    private Data data;

    @Setter
    @Getter
    @Schema(description = "Data container following JSON API standard")
    public static class Data {
        @Schema(
                description = "Resource type",
                example = "products",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        private String type;

        @Valid
        @NotNull(message = "Attributes are required")
        @Schema(
                description = "Inventory attributes and properties",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        private InventoryRequestDto attributes;
    }

}