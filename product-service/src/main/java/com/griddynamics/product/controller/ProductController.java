package com.griddynamics.product.controller;

import com.griddynamics.product.model.ProductRequest;
import com.griddynamics.product.model.catalog.Product;
import com.griddynamics.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/uid/{uids}")
    public ResponseEntity<List<Product>> getProductsByUid(@PathVariable List<String> uids,
                                                          @RequestParam(name = "availableOnly", required = false, defaultValue = "true") Boolean availableOnly) {
        log.info("Receive product request for uids={} with parameters=[availability={}]", uids, availableOnly);
        return ResponseEntity.ok(productService.getProductsByUid(new ProductRequest(uids, availableOnly)));
    }

    @GetMapping("/sku/{skus}")
    public ResponseEntity<List<Product>> getProductsBySku(@PathVariable List<String> skus,
                                                          @RequestParam(name = "availableOnly", required = false, defaultValue = "true") Boolean availableOnly) {
        log.info("Receive product request for skus={} with parameters=[availability={}]", skus, availableOnly);
        return ResponseEntity.ok(productService.getProductsBySku(new ProductRequest(skus, availableOnly)));
    }
}
