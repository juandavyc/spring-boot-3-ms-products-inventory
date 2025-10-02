package com.juandavyc.product.controller;

import com.juandavyc.product.controller.helper.ProductRequestHelper;
import com.juandavyc.product.infrastructure.rest.dto.ProductApiPageResponse;
import com.juandavyc.product.infrastructure.rest.dto.ProductApiRequest;
import com.juandavyc.product.infrastructure.rest.dto.ProductApiResponse;
import com.juandavyc.product.infrastructure.rest.dto.error.JsonApiError;
import com.juandavyc.product.infrastructure.rest.dto.error.JsonApiErrorResponse;
import com.juandavyc.product.infrastructure.rest.dto.request.ProductRequestDto;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    // no voy hacer los comentarios en ingles, ya el tiempo no da.

    // @Container
//    @ServiceConnection
//    static PostgreSQLContainer<?> postgreSQLContainer =
//            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.2"));
//
//    @Test
//    void canEstablishedConnection() {
//        assertThat(postgreSQLContainer.isCreated()).isTrue();
//        assertThat(postgreSQLContainer.isRunning()).isTrue();
//    }

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldCreateProduct() {
        // datos
        var request = new ProductApiRequest();
        var data = new ProductApiRequest.Data();
        data.setType("products");
        data.setAttributes(new ProductRequestDto("Test Laptop", BigDecimal.valueOf(999.99)));
        request.setData(data);

        // ejecucion
        ResponseEntity<ProductApiResponse> createResponse =
                ProductRequestHelper.createProduct(
                        restTemplate,
                        request,
                        new ParameterizedTypeReference<>() {
                        }
                );

        // verificar la creacion
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertNotNull(createResponse.getBody());
        assertThat(createResponse.getBody().data().attributes().name()).isEqualTo("Test Laptop");
        // verificar el id
        assertThat(createResponse.getBody().data().id()).isNotNull();

    }

    @Test
    void shouldReturnConflict_whenProductAlreadyExists() {

        var createRequest = new ProductApiRequest();
        var data = new ProductApiRequest.Data();

        data.setType("products");
        data.setAttributes(new ProductRequestDto("Test Monitor", BigDecimal.valueOf(250.00)));
        createRequest.setData(data);

        ResponseEntity<ProductApiResponse> firstCreateResponse = ProductRequestHelper.createProduct(
                restTemplate, createRequest,
                new ParameterizedTypeReference<>() {
                });

        // intentar crear de nuevo
        ResponseEntity<JsonApiErrorResponse> secondCreateResponse = ProductRequestHelper.createProduct(
                restTemplate, createRequest,
                new ParameterizedTypeReference<>() {
                });

        // debe retornar un 409
        assertThat(secondCreateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldReturn400OnInvalidInput() {
        //
        var request = new ProductApiRequest();
        var data = new ProductApiRequest.Data();
        data.setType("products");
        // no voy a pasar el name
        data.setAttributes(new ProductRequestDto(null, BigDecimal.valueOf(10.00)));
        request.setData(data);
        //
        ResponseEntity<JsonApiErrorResponse> createResponse =
                ProductRequestHelper.createProduct(
                        restTemplate,
                        request,
                        new ParameterizedTypeReference<>() {
                        }
                );

        // Then debe retornar 400
        assertThat(createResponse.getStatusCode()).
                isEqualTo(HttpStatus.BAD_REQUEST);

        Assertions.assertNotNull(createResponse.getBody());

        // obtengo la respuesta
        JsonApiErrorResponse errorBody = createResponse.getBody();

        assertThat(errorBody.errors())
                .isNotNull();

        // debe haber 1 solo error, el de null en name
        assertThat(errorBody.errors()).hasSize(1);

        // obtener el primer error
        JsonApiError error = errorBody.errors().get(0);

        // valores estaticos
        assertThat(error.status()).isEqualTo("400");
        assertThat(error.code()).isEqualTo("VALIDATION_ERROR");
        assertThat(error.title()).isEqualTo("Validation Failed");
        assertThat(error.detail()).contains("Name is required");

        // pointers
        assertThat(error.meta().get("field")).isEqualTo("data.attributes.name");
        assertThat(error.source().pointer()).isEqualTo("/data/attributes/name");
    }


    private ProductApiRequest createData(String name, BigDecimal values) {
        var request = new ProductApiRequest();
        var data = new ProductApiRequest.Data();
        data.setType("products");
        data.setAttributes(new ProductRequestDto(name, values));
        request.setData(data);
        return request;
    }

    // get
    @Test
    void shouldGetAllProductsWithDefaultPagination() {
        for (int i = 0; i < 5; i++) {
            ResponseEntity<ProductApiResponse> createResponse =
                    ProductRequestHelper.createProduct(
                            restTemplate,
                            createData("Test Laptop" + i, BigDecimal.valueOf(14.4+ i)),
                            new ParameterizedTypeReference<>() {
                            }
                    );
        }

        String url = "/api/products";
        ResponseEntity<ProductApiPageResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        }
                );

        // HTTP 200
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // cuerpo NO es nulo
        assertThat(response.getBody()).isNotNull();

        //  productos creados tamaño
        assertThat(response.getBody().data()).hasSize(5);

        // Verificar metadatos
        assertThat(response.getBody().meta().currentPage()).isEqualTo(0);
        assertThat(response.getBody().meta().totalElements()).isEqualTo(5);
    }

    // get by id

    @Test
    void shouldGetProductById() {
        // crear
        var request = new ProductApiRequest();
        var data = new ProductApiRequest.Data();
        data.setType("products");
        data.setAttributes(new ProductRequestDto("Test Monitor 33", BigDecimal.valueOf(14.00)));
        request.setData(data);

        // Ejecutar la creacion
        ResponseEntity<ProductApiResponse> createResponse =
                ProductRequestHelper.createProduct(
                        restTemplate,
                        request,
                        new ParameterizedTypeReference<>() {
                        }
                );

        Assertions.assertNotNull(createResponse.getBody());
        String productUrl = createResponse.getBody().data().id();

        // When
        ResponseEntity<ProductApiResponse> getResponse =
                ProductRequestHelper.getProductById(restTemplate, "/" + productUrl, ProductApiResponse.class);

        // Then
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(getResponse.getBody());

        assertThat(getResponse.getBody().data().attributes().name()).isEqualTo("Test Monitor 33");
    }

    @Test
    void shouldReturn404_whenProductNotFound() {
        // dar un uuid que no existe
        String nonExistentId = UUID.randomUUID().toString();
        String nonExistentUrl = "/" + nonExistentId;

        // atrapar el error
        ResponseEntity<JsonApiErrorResponse> getResponse =
                ProductRequestHelper.getProductById(restTemplate, nonExistentUrl, JsonApiErrorResponse.class);

//        // verificar el 404
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//
//        // verificar el body
        Assertions.assertNotNull(getResponse.getBody());

        JsonApiErrorResponse errorBody = getResponse.getBody();
        assertThat(errorBody).isNotNull();

        assertThat(errorBody.errors()).hasSize(1);

        // verificar detalles
        JsonApiError error = errorBody.errors().getFirst();
        assertThat(error.status()).isEqualTo("404");
        assertThat(error.title()).isEqualTo("Product Not Found");
        assertThat(error.detail()).contains(nonExistentId);
    }


    // update

    @Test
    void shouldUpdateProductSuccessfully() {

        var request = new ProductApiRequest();
        var data = new ProductApiRequest.Data();
        data.setType("products");
        data.setAttributes(new ProductRequestDto("Ram Asus 1", BigDecimal.valueOf(10.00)));
        request.setData(data);

        ResponseEntity<ProductApiResponse> createResponse =
                ProductRequestHelper.createProduct(
                        restTemplate,
                        request,
                        new ParameterizedTypeReference<>() {
                        }
                );

        Assertions.assertNotNull(createResponse.getBody());

        String productUrl = createResponse.getBody().links().self();
        String productId = createResponse.getBody().data().id();


        // preparar nueva peticion
        var updateRequest = new ProductApiRequest();
        var dataUpdate = new ProductApiRequest.Data();
        dataUpdate.setType("products");
        dataUpdate.setAttributes(new ProductRequestDto("New Name", BigDecimal.valueOf(12.00)));
        updateRequest.setData(dataUpdate);

        ResponseEntity<ProductApiResponse> updateResponse =
                ProductRequestHelper.updateProduct(
                        restTemplate,
                        productUrl,
                        updateRequest,
                        ProductApiResponse.class
                );

        // verificar respuestas
        Assertions.assertNotNull(updateResponse.getBody());

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody().data().id()).isEqualTo(productId); // ID no debe cambiar
        assertThat(updateResponse.getBody().data().attributes().name()).isEqualTo("New Name");
        // veriricar precio
        assertThat(updateResponse.getBody().data().attributes().price()).isEqualTo(BigDecimal.valueOf(12.00));
    }

    @Test
    void shouldReturn404_whenUpdatingNonExistentProduct() {

        String nonExistentId = UUID.randomUUID().toString();
        String nonExistentUrl = "/api/products/" + nonExistentId;

        // preparar una solicitud
        var updateRequest = new ProductApiRequest();
        var data = new ProductApiRequest.Data();
        data.setType("products");
        data.setAttributes(new ProductRequestDto("Dummy Update", BigDecimal.valueOf(1.0)));
        updateRequest.setData(data);

        ResponseEntity<JsonApiErrorResponse> response =
                ProductRequestHelper.updateProduct(
                        restTemplate,
                        nonExistentUrl,
                        updateRequest,
                        JsonApiErrorResponse.class
                );

        //verificar el codigo  404
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        // validar el error
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().errors().getFirst().title()).isEqualTo("Product Not Found");

    }

    @Test
    void shouldReturn400_whenUpdateDataIsInvalid() {
        var request = new ProductApiRequest();
        var data = new ProductApiRequest.Data();
        data.setType("products");
        data.setAttributes(new ProductRequestDto("Ram Asus", BigDecimal.valueOf(10.00)));
        request.setData(data);

        ResponseEntity<ProductApiResponse> createResponse =
                ProductRequestHelper.createProduct(
                        restTemplate,
                        request,
                        new ParameterizedTypeReference<>() {
                        }
                );

        Assertions.assertNotNull(createResponse.getBody());

        String productUrl = createResponse.getBody().links().self();

        // preparar la solicitud - precio negativo
        var invalidUpdateRequest = new ProductApiRequest();
        var invalidData = new ProductApiRequest.Data();
        invalidData.setType("products");

        // forzar la falla
        invalidData.setAttributes(new ProductRequestDto("Update Name", BigDecimal.valueOf(-50.00)));
        invalidUpdateRequest.setData(invalidData);

        ResponseEntity<JsonApiErrorResponse> response =
                restTemplate.exchange(
                        productUrl,
                        HttpMethod.PUT,
                        new HttpEntity<>(invalidUpdateRequest),
                        new ParameterizedTypeReference<JsonApiErrorResponse>() {
                        }
                );

        // verificar el codigo 400
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    void shouldReturn409_whenUpdatingToDuplicateName() {

        // product A
        var requestA = new ProductApiRequest();
        var dataA = new ProductApiRequest.Data();
        dataA.setType("products");
        dataA.setAttributes(new ProductRequestDto("Ram A", BigDecimal.valueOf(10.00)));
        requestA.setData(dataA);

        ResponseEntity<ProductApiResponse> createResponseA =
                ProductRequestHelper.createProduct(
                        restTemplate,
                        requestA,
                        new ParameterizedTypeReference<>() {
                        }
                );

        // product B
        var requestB = new ProductApiRequest();
        var dataB = new ProductApiRequest.Data();
        dataB.setType("products");
        dataB.setAttributes(new ProductRequestDto("Ram B", BigDecimal.valueOf(10.00)));
        requestB.setData(dataB);

        ResponseEntity<ProductApiResponse> createResponseB =
                ProductRequestHelper.createProduct(
                        restTemplate,
                        requestB,
                        new ParameterizedTypeReference<>() {
                        }
                );


        Assertions.assertNotNull(createResponseA.getBody());

        String urlA = createResponseA.getBody().links().self();


        //wrong

        var requestError = new ProductApiRequest();
        var dataError = new ProductApiRequest.Data();
        dataError.setType("products");
        dataError.setAttributes(new ProductRequestDto("Ram B", BigDecimal.valueOf(10.00)));
        requestError.setData(dataError);
        ResponseEntity<JsonApiErrorResponse> response =
                restTemplate.exchange(
                        urlA,
                        HttpMethod.PUT,
                        new HttpEntity<>(requestError),
                        new ParameterizedTypeReference<>() {
                        }
                );

        // verificar el codigo 409
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

    }

    @Test
    void shouldSoftDeleteProductAndReturn200() {
        // 1
        var requestA = new ProductApiRequest();
        var dataA = new ProductApiRequest.Data();
        dataA.setType("products");
        dataA.setAttributes(new ProductRequestDto("Ram AB", BigDecimal.valueOf(10.00)));
        requestA.setData(dataA);

        ResponseEntity<ProductApiResponse> createResponse =
                ProductRequestHelper.createProduct(
                        restTemplate,
                        requestA,
                        new ParameterizedTypeReference<>() {
                        }
                );
        Assertions.assertNotNull(createResponse.getBody());

        String productUrl = createResponse.getBody().links().self();
        String productId = createResponse.getBody().data().id();

        // eliminar
        ResponseEntity<ProductApiResponse> deleteResponse =
                restTemplate.exchange(
                        productUrl,
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<>() {
                        }
                );

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponse.getBody()).isNotNull();
        assertThat(deleteResponse.getBody().data().id()).isEqualTo(productId);

        ResponseEntity<JsonApiErrorResponse> getAfterDeleteResponse =
                restTemplate.exchange(
                        productUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<JsonApiErrorResponse>() {
                        }
                );

        // asi sea soft, debe mostrar un notfound
        assertThat(getAfterDeleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturn404_whenDeletingNonExistentProduct() {
        //
        String nonExistentId = UUID.randomUUID().toString();
        String nonExistentUrl = "/api/products/" + nonExistentId;

        // intentar borrar
        ResponseEntity<JsonApiErrorResponse> deleteResponse =
                restTemplate.exchange(
                        nonExistentUrl,
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<JsonApiErrorResponse>() {
                        }
                );

        // verificar codigo
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        //
        JsonApiErrorResponse errorBody = deleteResponse.getBody();
        assertThat(errorBody).isNotNull();
        assertThat(errorBody.errors()).hasSize(1);

        JsonApiError error = errorBody.errors().getFirst();
        assertThat(error.status()).isEqualTo("404");
        assertThat(error.title()).isEqualTo("Product Not Found");
        assertThat(error.detail()).contains(nonExistentId);
    }

    @Test
    void shouldReturn500_whenServiceThrowsUnexpectedRuntimeException() throws Exception {


        var request = new ProductApiRequest();
        var data = new ProductApiRequest.Data();
        data.setType("products");

        data.setAttributes(null);
        request.setData(data);
        //
        ResponseEntity<JsonApiErrorResponse> response =
                ProductRequestHelper.createProduct(
                        restTemplate,
                        request,
                        new ParameterizedTypeReference<>() {
                        }
                );

        // 3
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        JsonApiErrorResponse errorBody = response.getBody();
        assertThat(errorBody).isNotNull();
        assertThat(errorBody.errors()).hasSize(1);

        JsonApiError error = errorBody.errors().getFirst();

        assertThat(error.status()).isEqualTo("500");
        assertThat(error.title()).isEqualTo("Internal Server Error");
        assertThat(error.detail()).isEqualTo("Attributes are required");

    }



}
