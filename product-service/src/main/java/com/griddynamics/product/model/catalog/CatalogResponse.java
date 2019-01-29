package com.griddynamics.product.model.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogResponse {
    private List<Product> products;
}
