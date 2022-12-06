package com.shop.server.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PENDING_PAYMENT("Pending payment"),
    FAILED("Failed"),
    PROCESSING("Processing"),
    COMPLETED("Completed"),
    ON_HOLD("On hold"),
    CANCELED("Canceled"),
    REFUNDED("Refunded");

    private final String name;
}
