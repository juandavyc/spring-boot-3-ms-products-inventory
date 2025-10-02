package com.juandavyc.product.infrastructure.rest.helper;


import com.juandavyc.product.domain.model.dto.ProductDto;
import com.juandavyc.product.domain.model.dto.ProductPageDto;
import com.juandavyc.product.infrastructure.rest.dto.ProductApiData;
import com.juandavyc.product.infrastructure.rest.dto.ProductApiResponse;
import com.juandavyc.product.infrastructure.rest.dto.ProductApiPageResponse;

import java.util.List;
//
public class JsonResponseBuilder {

    // resource identifier
    public static final String RESOURCE_TYPE = "products";
    // hateoas links
    public static final String BASE_PATH = "/api/products";

    // build a single product
    public static ProductApiResponse buildProduct(ProductDto product) {
        ProductApiData data = toJsonApiData(product);
        ProductApiResponse.Links links = new ProductApiResponse.Links(BASE_PATH + "/" + product.id());
        return new ProductApiResponse(data, links);
    }

    // build a paginated product
    // with links:  first, last, prev, next...

    public static ProductApiPageResponse buildProductPage(ProductPageDto page) {

        List<ProductApiData> data = page.products().stream()
                .map(JsonResponseBuilder::toJsonApiData)
                .toList();

        // build pagination links according to json api spec
        var links = new ProductApiPageResponse.PaginationLinks(
                buildLink(page.currentPage(), page.pageSize()),
                buildLink(0, page.pageSize()), // first
                buildLink(page.totalPages() - 1, page.pageSize()), // last
                // only include prev and next if they exist
                page.currentPage() > 0 ? buildLink(page.currentPage() - 1, page.pageSize()) : null, // prev
                page.currentPage() < page.totalPages() - 1 ? buildLink(page.currentPage() + 1, page.pageSize()) : null // next
        );

        var meta = new ProductApiPageResponse.PaginationMeta(
                page.totalElements(),
                page.totalPages(),
                page.pageSize(),
                page.currentPage()
        );

        return new ProductApiPageResponse(data, links, meta);
    }


    private static String buildLink(int page, int size) {
        return BASE_PATH + "?page=" + page + "&size=" + size;
    }
    // converts ProductDto to json api data structure
    private static ProductApiData toJsonApiData(ProductDto product) {
        return new ProductApiData(
                RESOURCE_TYPE,
                product.id().toString(),
                product
        );
    }


}
