package com.shop.server.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {

    EARRINGS("Ear rings"),
    RINGS("Rings"),
    NECKLACES("Necklaces"),
    BRACELETS("Bracelets");

    private final String name;
}
