package com.griddynamics.catalog.storage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.griddynamics.catalog.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@Repository
@Slf4j
public class ProductsStorage {
    private Map<String, Product> productsMap;
    private Multimap<String, String> skuToUid;

    private Supplier<Set<Product>> productsSupplier;

    @Autowired
    public ProductsStorage(Supplier<Set<Product>> productsSupplier) {
        this.productsSupplier = productsSupplier;
    }

    @PostConstruct
    public void init() {
        ImmutableMap.Builder<String, Product> productsMapBuilder = ImmutableMap.builder();
        ImmutableMultimap.Builder<String, String> skuToUidMapBuilder = new ImmutableMultimap.Builder<>();

        productsSupplier.get()
                .forEach(product -> {
                    productsMapBuilder.put(product.getUid(), product);
                    skuToUidMapBuilder.put(product.getSku(), product.getUid());
                });

        productsMap = productsMapBuilder.build();
        skuToUid = skuToUidMapBuilder.build();

        log.info("Loaded {} unique products and {} unique skus", productsMap.size(), skuToUid.keySet().size());
    }

    public Optional<Product> getProductByUid(String uid) {
        return Optional.ofNullable(productsMap.get(uid));
    }

    public List<Optional<Product>> getProductsBySku(String sku) {
        Collection<String> uids = skuToUid.get(sku);

        if (CollectionUtils.isEmpty(uids)) {
            log.debug("Sku {} is not found", sku);
            return Collections.emptyList();
        }

        return uids.stream()
                .map(uid -> Optional.ofNullable(productsMap.get(uid)))
                .collect(toList());
    }
}
