package com.griddynamics.product.service;

public class ServiceTimeOutException extends RuntimeException {
    public ServiceTimeOutException() {
    }

    @Override
    public String getMessage() {
        return "Server temporary unavailable";
    }
}
