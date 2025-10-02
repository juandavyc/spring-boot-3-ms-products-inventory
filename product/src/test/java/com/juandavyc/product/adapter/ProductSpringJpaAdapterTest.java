package com.juandavyc.product.adapter;


import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.infrastructure.adapter.ProductSpringJpaAdapter;
import com.juandavyc.product.infrastructure.adapter.entity.ProductEntity;
import com.juandavyc.product.infrastructure.adapter.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ProductSpringJpaAdapterTest {
    //    @Transactional hacer roolback
    @Autowired
    private ProductSpringJpaAdapter underTest;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @Test
    void save_ShouldPersistNewProduct() {

        Product newProduct = new Product(null, "Tested Product", new BigDecimal("99.99"), false);
        Product savedProduct = underTest.save(newProduct);

        assertThat(savedProduct)
                .isNotNull();
        assertThat(savedProduct.getId())
                .isNotNull();
        assertThat(savedProduct.getName())
                .isEqualTo(newProduct.getName());

        assertThat(productRepository.count())
                .isEqualTo(1L);

        assertThat(productRepository.findById(savedProduct.getId()))
                .isPresent()
                .get()
                .satisfies(entity -> {
                    assertThat(entity.getName()).isEqualTo("Tested Product");
                    assertThat(entity.getDeleted()).isFalse();
                });
    }

    @Transactional
    @Test
    void findById_ShouldReturnProduct_WhenActiveProductExists() {

        ProductEntity activeEntity = new ProductEntity();
        activeEntity.setName("Active Widget");
        activeEntity.setPrice(new BigDecimal("10.00"));
        activeEntity.setDeleted(false);
        activeEntity = productRepository.save(activeEntity);

        Optional<Product> result = underTest.findById(activeEntity.getId());

        assertThat(result)
                .isPresent()
                .get()
                .satisfies(product -> {
                    assertThat(product.getName()).isEqualTo("Active Widget");
                    assertThat(product.getDeleted()).isFalse();
                });
    }

    @Transactional
    @Test
    void findAll_ShouldReturnOnlyActiveProducts_WithCorrectPagination() {

        setupProduct("Product A", new BigDecimal("10.00"), false); // Active 1
        setupProduct("Deleted B", new BigDecimal("20.00"), true);  // Deleted
        setupProduct("Product C", new BigDecimal("30.00"), false); // Active 2
        setupProduct("Product D", new BigDecimal("40.00"), false); // Active 3
        setupProduct("Deleted E", new BigDecimal("50.00"), true);  // Deleted


        int offset = 1;
        int limit = 2;
        int page = 1;
        offset = page * limit;

        List<Product> results = underTest.findAll(offset, limit);

        assertThat(results.getFirst().getName())
                .isEqualTo("Product D");

        assertThat(results.stream()
                .noneMatch(Product::getDeleted)).isTrue();
    }

    @Transactional
    @Test
    void count_ShouldReturnCountOfOnlyActiveProducts() {

        setupProduct("P1", new BigDecimal("1"), false);
        setupProduct("P2", new BigDecimal("2"), false);
        setupProduct("Deleted P3", new BigDecimal("3"), true); // Should be excluded
        setupProduct("P4", new BigDecimal("4"), false);

        Long count = underTest.count();

        assertThat(count).isEqualTo(3L);
    }

    @Transactional
    @Test
    void existsByNameAndDeletedIsFalse_ShouldReturnTrue_WhenActiveNameExists() {
        String name = "Active Name Check";
        setupProduct(name, new BigDecimal("100"), false); // Active
        assertThat(underTest.existsByNameAndDeletedIsFalse(name)).isTrue();
    }

    @Transactional
    @Test
    void existsByNameAndDeletedIsFalse_ShouldReturnFalse_WhenOnlyDeletedNameExists() {
        String name = "Deleted Name Check";
        setupProduct(name, new BigDecimal("100"), true); // Deleted
        assertThat(underTest.existsByNameAndDeletedIsFalse(name)).isFalse();
    }

    @Transactional
    @Test
    void existsByNameAndDeletedIsFalse_ShouldReturnFalse_WhenNameDoesNotExist() {
        String name = "Non-Existent Name";
        assertThat(underTest.existsByNameAndDeletedIsFalse(name)).isFalse();
    }

    private void setupProduct(String name, BigDecimal price, boolean deleted) {
        ProductEntity entity = new ProductEntity();
        entity.setName(name);
        entity.setPrice(price);
        entity.setDeleted(deleted);
        productRepository.save(entity);
    }


}
