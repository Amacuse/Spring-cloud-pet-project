package com.griddynamics.product.service;


import com.griddynamics.product.model.ProductRequest;
import com.griddynamics.product.model.catalog.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductsByUid(ProductRequest productRequest);

    List<Product> getProductsBySku(ProductRequest productRequest);
}
