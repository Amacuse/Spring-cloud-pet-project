package com.griddynamics.product.model.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private Map<String, Boolean> availabilityMap;
}
