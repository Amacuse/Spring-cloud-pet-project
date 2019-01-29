package com.griddynamics.product.service.supplier;

import com.griddynamics.product.model.inventory.InventoryResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@Slf4j
public class InventorySupplier extends AbstractSupplier<InventoryResponse> {
    private static final String INVENTORY_SERVICE_URL = "http://inventory-service/v1/inventory/availability/{uids}";

    @Autowired
    public InventorySupplier(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @HystrixCommand(fallbackMethod = "getAvailabilityFallback")
    public Map<String, Boolean> getAvailability(List<String> uids) {
        return extractBody(get(INVENTORY_SERVICE_URL, uids));
    }

    @Override
    protected Class<InventoryResponse> getClazz() {
        return InventoryResponse.class;
    }

    private Map<String, Boolean> extractBody(ResponseEntity<InventoryResponse> entity) {
        if (entity.getStatusCode() != HttpStatus.OK) {
            throw new BadRequestException(String.format("Server responded with %s status code", entity.getStatusCode()));
        }

        InventoryResponse inventoryResponse = entity.getBody();
        if (isNull(inventoryResponse) || isEmpty(inventoryResponse.getAvailabilityMap())) {
            return Collections.emptyMap();
        }

        return inventoryResponse.getAvailabilityMap();
    }

    private Map<String, Boolean> getAvailabilityFallback(List<String> keys) {
        return null;
    }
}
