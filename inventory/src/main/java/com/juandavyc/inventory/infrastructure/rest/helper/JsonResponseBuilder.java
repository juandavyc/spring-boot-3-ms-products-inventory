package com.juandavyc.inventory.infrastructure.rest.helper;


import com.juandavyc.inventory.domain.model.Product;
import com.juandavyc.inventory.domain.model.dto.InventoryDto;
import com.juandavyc.inventory.domain.model.dto.ProductDto;
import com.juandavyc.inventory.infrastructure.rest.dto.InventoryApiResponse;
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

        InventoryData inventoryData = new InventoryData("inventories", id, attributes, relationships);


        List<ProductData> included = inventory.included().stream()
                .map(p -> new ProductData("products", id, new ProductData.ProductAttributes(
                        p.id(), p.name(), p.price(), p.deleted()
                )))
                .toList();

        return new InventoryApiResponse(inventoryData, included);

    }

//    // build a paginated
//    // with links:  first, last, prev, next...
//
//
//    private static String buildLink(int page, int size) {
//        return BASE_PATH + "?page=" + page + "&size=" + size;
//    }
//    // converts ProductDto to json api data structure
//    private static InventoryApiData toJsonApiData(InventoryDto inventory) {
//        return new InventoryApiData(
//                RESOURCE_TYPE,
//                inventory.productId().toString(),
//                inventory
//        );
//    }


}
