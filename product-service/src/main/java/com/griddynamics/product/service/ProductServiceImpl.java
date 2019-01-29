package com.griddynamics.product.service;

import com.griddynamics.product.model.ProductRequest;
import com.griddynamics.product.model.catalog.Product;
import com.griddynamics.product.service.supplier.InventorySupplier;
import com.griddynamics.product.service.supplier.ProductSupplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductSupplier productSupplier;
    private InventorySupplier inventorySupplier;

    @Autowired
    public ProductServiceImpl(ProductSupplier productSupplier, InventorySupplier inventorySupplier) {
        this.productSupplier = productSupplier;
        this.inventorySupplier = inventorySupplier;
    }

    @Override
    public List<Product> getProductsByUid(ProductRequest productRequest) {
        List<Product> products = productSupplier.getProductsByUids(productRequest.getKeys());
        return processProducts(products, productRequest.isAvailableOnly());
    }

    @Override
    public List<Product> getProductsBySku(ProductRequest productRequest) {
        List<Product> products = productSupplier.getProductsBySkus(productRequest.getKeys());
        return processProducts(products, productRequest.isAvailableOnly());
    }

    private List<Product> processProducts(List<Product> products, boolean availableOnly) {

        checkFallback(products);

        if (products.isEmpty() || !availableOnly) {
            return products;
        }

        Map<String, Boolean> availability = inventorySupplier.getAvailability(products.stream()
                .map(Product::getUid)
                .collect(Collectors.toList()));

        checkFallback(availability);

        if (CollectionUtils.isEmpty(availability)) {
            return products;
        }

        return products.stream()
                .filter(product -> BooleanUtils.isTrue(availability.get(product.getUid())))
                .collect(Collectors.toList());
    }

    private void checkFallback(Object o) {
        if (isNull(o)) {
            throw new ServiceTimeOutException();
        }
    }
}
