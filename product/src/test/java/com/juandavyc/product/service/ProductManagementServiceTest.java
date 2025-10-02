package com.juandavyc.product.service;


import com.juandavyc.product.application.exceptions.ProductAlreadyExistsException;
import com.juandavyc.product.application.exceptions.ProductNotFoundException;
import com.juandavyc.product.application.mapper.ProductDtoMapper;
import com.juandavyc.product.application.mapper.ProductRequestMapper;
import com.juandavyc.product.application.service.ProductManagementService;
import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.domain.model.dto.ProductDto;
import com.juandavyc.product.domain.model.dto.ProductPageDto;
import com.juandavyc.product.domain.port.ProductPersistencePort;
import com.juandavyc.product.infrastructure.rest.dto.request.ProductRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductManagementServiceTest {

    ProductManagementService underTest;

    @Mock
    private ProductPersistencePort productPersistencePort;
    @Mock
    private ProductRequestMapper productRequestMapper;
    @Mock
    private ProductDtoMapper productDtoMapper;

    @Captor
    ArgumentCaptor<Product> productCaptor;

    @BeforeEach
    void setUp() {
        underTest = new ProductManagementService(
                productPersistencePort,
                productRequestMapper,
                productDtoMapper
        );
    }

    @Test
    void create_ShouldCreateProductSuccessfully_AndReturnDto() {

        UUID productId = UUID.randomUUID();
        String productName = "Product";
        BigDecimal productPrice = new BigDecimal("50.00");
        ProductRequestDto request = new ProductRequestDto(productName, productPrice);
        // mapear
        Product productFromMapper = new Product(null, productName, productPrice, false);
        // guardo
        Product productSaved = new Product(productId, productName, productPrice, false);
        // dto de salida
        ProductDto expectedDto = new ProductDto(productId, productName, productPrice, false);
        // existe el nombre?
        when(productPersistencePort.existsByNameAndDeletedIsFalse(productName))
                .thenReturn(false);
        // primera transformacion
        when(productRequestMapper.toDomain(request))
                .thenReturn(productFromMapper);
        // capturamos el valor
        when(productPersistencePort.save(productCaptor.capture()))
                .thenReturn(productSaved);
        // simula la salida
        when(productDtoMapper.toDto(productSaved))
                .thenReturn(expectedDto);

        ProductDto actualDto = underTest.create(request);

        // que no sea null
        assertThat(actualDto).isNotNull();
        // que sean iguales
        assertThat(actualDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedDto);
        // que se llamo el validador
        verify(productPersistencePort)
                .existsByNameAndDeletedIsFalse(productName);

        // que se llamo al metodo save
        verify(productPersistencePort)
                .save(any(Product.class));

        // verifica que debe ser false
        Product productPassedToSave = productCaptor.getValue();
        assertThat(productPassedToSave.getDeleted())
                .as("The 'deleted' flag must be set to false before saving")
                .isFalse();
    }

    @Test
    void create_ShouldThrowProductAlreadyExistsException_WhenNameExists() {
        // simular un producto
        String existingName = "Existing Product";
        BigDecimal price = new BigDecimal("10.00");
        ProductRequestDto request = new ProductRequestDto(existingName, price);
        // si existe
        when(productPersistencePort.existsByNameAndDeletedIsFalse(existingName))
                .thenReturn(true);
        // debe lanzar un error
        assertThatThrownBy(() -> underTest.create(request))
                .isInstanceOf(ProductAlreadyExistsException.class);

        // nunca llamo a estos mapper
        verify(productRequestMapper, never()).toDomain(any());
        verify(productDtoMapper, never()).toDto(any());
        verify(productPersistencePort, never()).save(any());
    }

    @Test
    void getById_ShouldReturnProductDto_WhenProductExists() {

        UUID id = UUID.randomUUID();
        BigDecimal price = new BigDecimal("20.00");
        //
        Product foundProduct = new Product(id, "Test Product", price, false);
        // dto respuesta
        ProductDto expectedDto = new ProductDto(id, "Test Product", price, false);
        // simula que encontro
        when(productPersistencePort.findById(id))
                .thenReturn(Optional.of(foundProduct));
        // simula transformacion del dto
        when(productDtoMapper.toDto(foundProduct))
                .thenReturn(expectedDto);

        ProductDto actualDto = underTest.getById(id);

        // comprar los dos objetos
        assertThat(actualDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedDto);

        // verificar que se llamo al puerto
        verify(productPersistencePort).findById(id);
        // verifica que se llamo al mapper
        verify(productDtoMapper).toDto(foundProduct);
    }

    @Test
    void getById_ShouldThrowProductNotFoundException_WhenProductDoesNotExist() {
        UUID id = UUID.randomUUID();
        // simular no se encontro
        when(productPersistencePort.findById(id))
                .thenReturn(Optional.empty());
        // se lanza la excepcion y que tenga el id el mensaje
        assertThatThrownBy(() -> underTest.getById(id))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(id.toString());
        // verificar llamada al puerto
        verify(productPersistencePort).findById(id);
        // nunca se llamo al mapper dto
        verify(productDtoMapper, never()).toDto(any());
    }

    @Test
    void getAll_ShouldReturnCorrectlyPagedData() {

        final int page = 1;
        final int size = 3;
        final int expectedOffset = page * size;
        final long totalElements = 7L;
        final int expectedTotalPages = 3;

        // productos dominio
        Product product4 = new Product(UUID.randomUUID(), "Product 4", new BigDecimal("40.00"), false);
        Product product5 = new Product(UUID.randomUUID(), "Product 5", new BigDecimal("50.00"), false);
        Product product6 = new Product(UUID.randomUUID(), "Product 6", new BigDecimal("60.00"), false);
        List<Product> domainProducts = List.of(product4, product5, product6);

        // productos respuesta
        ProductDto dto4 = new ProductDto(product4.getId(), "Product 4", new BigDecimal("40.00"), false);
        ProductDto dto5 = new ProductDto(product5.getId(), "Product 5", new BigDecimal("50.00"), false);
        ProductDto dto6 = new ProductDto(product6.getId(), "Product 6", new BigDecimal("60.00"), false);
        List<ProductDto> expectedDtos = List.of(dto4, dto5, dto6);

        // dto de respuesta
        ProductPageDto expectedPageDto = new ProductPageDto(
                expectedDtos,
                totalElements,
                expectedTotalPages,
                page,
                size
        );
        // simular conteo
        when(productPersistencePort.count())
                .thenReturn(totalElements);
        // simular busqueda paginada
        when(productPersistencePort.findAll(expectedOffset, size))
                .thenReturn(domainProducts);

        // simula el mapeo
        when(productDtoMapper.toDto(product4)).thenReturn(dto4);
        when(productDtoMapper.toDto(product5)).thenReturn(dto5);
        when(productDtoMapper.toDto(product6)).thenReturn(dto6);

        ProductPageDto actualPageDto = underTest.getAll(page, size);
        // compara
        assertThat(actualPageDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedPageDto);

        // verifica que el "offset" se calculo y uso correctamente
        verify(productPersistencePort).findAll(expectedOffset, size);
        // que se consulto el total de elementos
        verify(productPersistencePort).count();
        // que el mapper se llamo n veces
        verify(productDtoMapper, times(domainProducts.size()))
                .toDto(any(Product.class));
    }

    // update
    @Test
    void update_ShouldThrowProductNotFoundException_WhenProductDoesNotExist() {

        UUID nonExistentId = UUID.randomUUID();
        ProductRequestDto request = new ProductRequestDto("New Name", new BigDecimal("100.00"));
        // no se encuentra producto
        when(productPersistencePort.findById(nonExistentId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.update(nonExistentId, request))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(nonExistentId.toString());

        // nunca se llamo save o la siguiente validacion
        verify(productPersistencePort, never()).save(any());
        verify(productPersistencePort, never()).existsByNameAndDeletedIsFalse(anyString());
    }

    @Test
    void update_ShouldUpdateNameAndPrice_Successfully() {

        UUID id = UUID.randomUUID();
        Product existingProduct = new Product(id, "Old Name", new BigDecimal("50.00"), false);

        String newName = "Updated Name";
        BigDecimal newPrice = new BigDecimal("75.00");
        ProductRequestDto request = new ProductRequestDto(newName, newPrice);

        // producto actualizado
        Product expectedProductToSave = new Product(id, newName, newPrice, false);

        // producto despues de guardar
        Product savedProduct = expectedProductToSave;

        // dto de respuesta
        ProductDto expectedDto = new ProductDto(id, newName, newPrice, false);
        // existe el producto
        when(productPersistencePort.findById(id))
                .thenReturn(Optional.of(existingProduct));
        // el nombre esta disponible
        when(productPersistencePort.existsByNameAndDeletedIsFalse(newName))
                .thenReturn(false);

        // captura
        when(productPersistencePort.save(productCaptor.capture()))
                .thenReturn(savedProduct);
        // simular respuesta
        when(productDtoMapper.toDto(savedProduct))
                .thenReturn(expectedDto);

        ProductDto actualDto = underTest.update(id, request);

        assertThat(actualDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedDto);

        // que se llamo a verificacion de nombre
        verify(productPersistencePort).existsByNameAndDeletedIsFalse(newName);
        // que se llamo a "save"
        verify(productPersistencePort).save(any(Product.class));

        // verifica el contenido que paso por "save"
        Product productPassedToSave = productCaptor.getValue();
        assertThat(productPassedToSave.getName()).isEqualTo(newName);
        assertThat(productPassedToSave.getPrice()).isEqualTo(newPrice);
        assertThat(productPassedToSave.getId()).isEqualTo(id);

    }

    @Test
    void update_ShouldThrowProductAlreadyExistsException_WhenNewNameConflicts() {

        UUID id = UUID.randomUUID();
        Product existingProduct = new Product(id, "Original Name", new BigDecimal("50.00"), false);
        // nombre con conflicto
        String conflictingName = "Taken Name";
        ProductRequestDto request = new ProductRequestDto(conflictingName, new BigDecimal("75.00"));

        when(productPersistencePort.findById(id))
                .thenReturn(Optional.of(existingProduct));

        // nombre no disponible
        when(productPersistencePort.existsByNameAndDeletedIsFalse(conflictingName))
                .thenReturn(true);

        assertThatThrownBy(() -> underTest.update(id, request))
                .isInstanceOf(ProductAlreadyExistsException.class)
                .hasMessageContaining(conflictingName);

        // verificar que se llamo al metodo existsByNameAndDeletedIsFalse
        verify(productPersistencePort).existsByNameAndDeletedIsFalse(conflictingName);
        // nunva se llamo a "save"
        verify(productPersistencePort, never()).save(any());
    }

    // delete
    @Test
    void softDelete_ShouldThrowProductNotFoundException_WhenProductDoesNotExist() {

        UUID nonExistentId = UUID.randomUUID();
        // producto no encontrado
        when(productPersistencePort.findById(nonExistentId))
                .thenReturn(Optional.empty());
        // que se lanza la excepcion
        assertThatThrownBy(() -> underTest.softDelete(nonExistentId))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(nonExistentId.toString());

        // nunca se llamo a "save"
        verify(productPersistencePort, never()).save(any());
        // el "mapper" a dto no fue llamado
        verify(productDtoMapper, never()).toDto(any());
    }

    @Test
    void softDelete_ShouldSetDeletedFlagToTrue_AndReturnDto() {

        UUID id = UUID.randomUUID();
        BigDecimal price = new BigDecimal("100.00");

        Product existingProduct = new Product(id, "Test Product", price, false);

        Product productMarkedAsDeleted = new Product(id, "Test Product", price, true);

        ProductDto expectedDto = new ProductDto(id, "Test Product", price, true);

        when(productPersistencePort.findById(id))
                .thenReturn(Optional.of(existingProduct));

        when(productPersistencePort.save(productCaptor.capture()))
                .thenReturn(productMarkedAsDeleted);

        when(productDtoMapper.toDto(productMarkedAsDeleted))
                .thenReturn(expectedDto);

        ProductDto actualDto = underTest.softDelete(id);

        assertThat(actualDto).isEqualTo(expectedDto);
        assertThat(actualDto.deleted()).isTrue();

        verify(productPersistencePort).findById(id);
        verify(productPersistencePort).save(any(Product.class));

        Product productPassedToSave = productCaptor.getValue();
        assertThat(productPassedToSave.getDeleted())
                .as("The 'deleted' flag must be set to true before saving.")
                .isTrue();
    }

}
