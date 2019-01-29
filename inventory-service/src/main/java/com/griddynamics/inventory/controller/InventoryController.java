package com.griddynamics.inventory.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RestController
@RequestMapping("/v1/inventory")
@Slf4j
public class InventoryController {

    @GetMapping("/availability/{uids}")
    public ResponseEntity<InventoryResponse> getAvailability(@PathVariable List<String> uids) {
        log.info("Get inventory request for uids={}", uids);
        Map<String, Boolean> availabilityMap = uids.stream()
                .collect(toMap(k -> k, v -> RandomUtils.nextBoolean()));

        return ResponseEntity.ok(new InventoryResponse(availabilityMap));
    }

    @Data
    @AllArgsConstructor
    private class InventoryResponse {
        private Map<String, Boolean> availabilityMap;
    }
}
