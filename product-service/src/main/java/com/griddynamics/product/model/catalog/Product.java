package com.griddynamics.product.model.catalog;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Have to be placed in the separate module aka 'catalog-model'.
 * For learning purposes keep it here
 * */
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    @EqualsAndHashCode.Include
    private String uid;
    private String sku;

    private String nameTitle;
    private String description;

    private BigDecimal listPrice;
    private BigDecimal salePrice;

    private String category;
    private String categoryTree;

    private String averageProductRating;
    private String productUrl;
    private String productImageUrls;
    private String brand;

    private Integer totalNumberReviews;
    private String reviews;
}
