package com.shop.server.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Material {

    SILVER("Silver"),
    GOLD("Gold"),
    TWO_TONE("Two tone"),
    PEARL("Pearl"),
    HAND_PAINTED("Hand painted");

    private final String name;
}
