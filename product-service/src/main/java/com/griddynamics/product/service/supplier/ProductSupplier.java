package com.griddynamics.product.service.supplier;

import com.griddynamics.product.model.catalog.CatalogResponse;
import com.griddynamics.product.model.catalog.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@Slf4j
public class ProductSupplier extends AbstractSupplier<CatalogResponse> {
    private static final String CATALOG_SERVICE_UID_URL = "http://catalog-service/v1/catalog/uid/{uids}";
    private static final String CATALOG_SERVICE_SKU_URL = "http://catalog-service/v1/catalog/sku/{uids}";

    @Autowired
    public ProductSupplier(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @HystrixCommand(fallbackMethod = "getProductsFallback")
    public List<Product> getProductsByUids(List<String> uids) {
        return extractBody(get(CATALOG_SERVICE_UID_URL, uids));
    }

    @HystrixCommand(fallbackMethod = "getProductsFallback")
    public List<Product> getProductsBySkus(List<String> skus) {
        return extractBody(get(CATALOG_SERVICE_SKU_URL, skus));
    }

    @Override
    protected Class<CatalogResponse> getClazz() {
        return CatalogResponse.class;
    }

    private List<Product> extractBody(ResponseEntity<CatalogResponse> entity) {
        if (entity.getStatusCode() != HttpStatus.OK) {
            throw new BadRequestException(String.format("Server responded with %s status code", entity.getStatusCode()));
        }

        CatalogResponse catalogResponse = entity.getBody();
        if (isNull(catalogResponse) || isEmpty(catalogResponse.getProducts())) {
            return Collections.emptyList();
        }

        return catalogResponse.getProducts();
    }

    private List<Product> getProductsFallback(List<String> keys) {
        return null;
    }
}
