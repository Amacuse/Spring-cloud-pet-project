package com.griddynamics.catalog.service;

import com.griddynamics.catalog.model.Product;
import com.griddynamics.catalog.storage.ProductsStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements CatalogService {
    private final ProductsStorage productsStorage;

    @Autowired
    public CatalogServiceImpl(ProductsStorage productsStorage) {
        this.productsStorage = productsStorage;
    }

    @Override
    public List<Product> getProductsByUids(List<String> uids) {
        return uids.stream()
                .map(productsStorage::getProductByUid)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductsBySkus(List<String> skus) {
        return skus.stream()
                .flatMap(sku -> productsStorage.getProductsBySku(sku).stream())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
