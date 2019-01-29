package com.griddynamics.product.service.supplier;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public abstract class AbstractSupplier<T> {
    private static final String DELIMITER = ",";

    private final RestTemplate restTemplate;

    public AbstractSupplier(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected abstract Class<T> getClazz();

    protected ResponseEntity<T> get(String url, List<String> keys) {
        String join = String.join(DELIMITER, keys);
        return restTemplate.getForEntity(url, getClazz(), join);
    }
}
