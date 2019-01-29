package com.griddynamics.catalog.service;

import com.griddynamics.catalog.model.Product;

import java.util.List;

public interface CatalogService {
    List<Product> getProductsByUids(List<String> uids);

    List<Product> getProductsBySkus(List<String> skus);
}
