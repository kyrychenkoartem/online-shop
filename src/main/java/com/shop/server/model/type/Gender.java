package com.shop.server.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    MALE("Male"),
    FEMALE("Female"),
    TRANSGENDER("Transgender"),
    NON_BINARY("Non binary");

    private final String name;
}
