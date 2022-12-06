package com.shop.server.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardType {

    DEBIT("Debit"),
    CREDIT("Credit"),
    GIFT("Gift");

    private final String name;
}
