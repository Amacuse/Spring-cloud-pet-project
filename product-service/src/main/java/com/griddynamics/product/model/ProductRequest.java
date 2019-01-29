package com.griddynamics.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductRequest {
    private List<String> keys;
    private boolean availableOnly;
}
