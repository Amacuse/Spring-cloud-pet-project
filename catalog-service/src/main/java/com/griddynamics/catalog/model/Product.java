package com.griddynamics.catalog.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

import static lombok.EqualsAndHashCode.Include;

@Data
@Builder
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    @Include
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
