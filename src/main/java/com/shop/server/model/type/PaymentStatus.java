package com.shop.server.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    SUBMITTED("Submitted"),
    COMPLETED("Completed"),
    WITHDRAWN("Withdrawn"),
    REFUNDED("Refunded");

    private final String name;
}
