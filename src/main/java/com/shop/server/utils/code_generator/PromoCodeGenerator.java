package com.shop.server.utils.code_generator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PromoCodeGenerator {

    private static final Integer START_UPPER_CASE_VALUE = 65;
    private static final Integer END_UPPER_CASE_VALUE = 91;
    private static final Integer START_LOWER_CASE_VALUE = 97;
    private static final Integer END_LOWER_CASE_VALUE = 123;
    private static final Integer LOOP_COUNT = 10;


    public static String generate(Integer value) {
        StringBuilder builder = new StringBuilder();
        builder.append(RandomUtils.nextInt());
        for (int i = 0; i < LOOP_COUNT; i++) {
            builder.append(getLowerCharacterCase());
            builder.append(getUpperCharacterCase());
        }
        return builder.toString();
    }

    private static Character getLowerCharacterCase() {
        return (char) RandomUtils.nextInt(START_LOWER_CASE_VALUE, END_LOWER_CASE_VALUE);
    }

    private static Character getUpperCharacterCase() {
        return (char) RandomUtils.nextInt(START_UPPER_CASE_VALUE, END_UPPER_CASE_VALUE);
    }
}
