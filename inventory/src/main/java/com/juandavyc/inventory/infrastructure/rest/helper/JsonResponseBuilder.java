package com.juandavyc.inventory.infrastructure.rest.helper;

import com.juandavyc.inventory.domain.model.dto.InventoryDto;
import com.juandavyc.inventory.infrastructure.rest.dto.api.InventoryApiResponse;
import com.juandavyc.inventory.infrastructure.rest.dto.InventoryData;
import com.juandavyc.inventory.infrastructure.rest.dto.ProductData;

import java.util.List;
import java.util.UUID;

//
public class JsonResponseBuilder {

    // resource identifier
    public static final String RESOURCE_TYPE = "inventory";
    // hateoas links
    public static final String BASE_PATH = "/api/inventories";

    // build a single
    public static InventoryApiResponse buildInventory(InventoryDto inventory) {

        UUID id = inventory.productId();

        var productRef = new InventoryData.RelationshipData("products", id);
        var productRelationship = new InventoryData.ProductRelationship(productRef);
        var relationships = new InventoryData.InventoryRelationships(productRelationship);
        var attributes = new InventoryData.InventoryAttributes(inventory.quantity());
        var inventoryData = new InventoryData("inventories", id, attributes, relationships);

        List<ProductData> included = inventory.included().stream()
                .map(p -> new ProductData("products", id, new ProductData.ProductAttributes(
                        p.id(), p.name(), p.price(), p.deleted()
                )))
                .toList();

        return new InventoryApiResponse(inventoryData, included);
    }
}
