package com.juandavyc.inventory.infrastructure.rest.helper;


import com.juandavyc.inventory.domain.model.dto.InventoryDto;
import com.juandavyc.inventory.domain.model.dto.InventoryPageDto;
import com.juandavyc.inventory.infrastructure.rest.dto.InventoryApiData;
import com.juandavyc.inventory.infrastructure.rest.dto.InventoryApiPageResponse;
import com.juandavyc.inventory.infrastructure.rest.dto.InventoryApiResponse;

import java.util.List;
//
public class JsonResponseBuilder {

    // resource identifier
    public static final String RESOURCE_TYPE = "inventory";
    // hateoas links
    public static final String BASE_PATH = "/api/inventories";

    // build a single
    public static InventoryApiResponse buildInventory(InventoryDto inventory) {
        InventoryApiData data = toJsonApiData(inventory);
        InventoryApiResponse.Links links = new InventoryApiResponse.Links(BASE_PATH + "/" + inventory.productId());
        return new InventoryApiResponse(data, links);
    }

    // build a paginated
    // with links:  first, last, prev, next...

    public static InventoryApiPageResponse buildProductPage(InventoryPageDto page) {

        List<InventoryApiData> data = page.inventories().stream()
                .map(JsonResponseBuilder::toJsonApiData)
                .toList();

        // build pagination links according to json api spec
        var links = new InventoryApiPageResponse.PaginationLinks(
                buildLink(page.currentPage(), page.pageSize()),
                buildLink(0, page.pageSize()), // first
                buildLink(page.totalPages() - 1, page.pageSize()), // last
                // only include prev and next if they exist
                page.currentPage() > 0 ? buildLink(page.currentPage() - 1, page.pageSize()) : null, // prev
                page.currentPage() < page.totalPages() - 1 ? buildLink(page.currentPage() + 1, page.pageSize()) : null // next
        );

        var meta = new InventoryApiPageResponse.PaginationMeta(
                page.totalElements(),
                page.totalPages(),
                page.pageSize(),
                page.currentPage()
        );

        return new InventoryApiPageResponse(data, links, meta);
    }


    private static String buildLink(int page, int size) {
        return BASE_PATH + "?page=" + page + "&size=" + size;
    }
    // converts ProductDto to json api data structure
    private static InventoryApiData toJsonApiData(InventoryDto inventory) {
        return new InventoryApiData(
                RESOURCE_TYPE,
                inventory.productId().toString(),
                inventory
        );
    }


}
