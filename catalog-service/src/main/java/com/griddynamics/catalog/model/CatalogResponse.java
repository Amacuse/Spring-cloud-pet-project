package com.griddynamics.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CatalogResponse {
    private List<Product> products;
}
