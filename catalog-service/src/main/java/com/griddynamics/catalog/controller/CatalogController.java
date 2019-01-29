package com.griddynamics.catalog.controller;

import com.griddynamics.catalog.model.CatalogResponse;
import com.griddynamics.catalog.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/catalog")
@Slf4j
public class CatalogController {

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/uid/{uids}")
    public ResponseEntity<CatalogResponse> getProductsByUids(@PathVariable List<String> uids) {
        log.info("Get catalog request for uids={}", uids);
        return ResponseEntity.ok(new CatalogResponse(catalogService.getProductsByUids(uids)));
    }

    @GetMapping("/sku/{skus}")
    public ResponseEntity<CatalogResponse> getProductsBySkus(@PathVariable List<String> skus) {
        log.info("Get catalog request for skus={}", skus);
        return ResponseEntity.ok(new CatalogResponse(catalogService.getProductsBySkus(skus)));
    }
}
