package com.shop.server.utils;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExpiryDateFormatter {

    private static final String EXPIRY_DATE_REG_EXP = "^(0[1-9]|1[0-2])\\/?([0-9]{2})$";
    private static final Pattern PATTERN = Pattern.compile(EXPIRY_DATE_REG_EXP);

    public static boolean isMatch(String context) {
        return PATTERN.matcher(context).matches();
    }
}
