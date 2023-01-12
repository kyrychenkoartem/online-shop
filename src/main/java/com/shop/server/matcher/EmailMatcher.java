package com.shop.server.matcher;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailMatcher {

    private static final String EMAIL_REG_EXP = "^[A-Za-z0-9._]+@[A-Za-z0-9]+\\.[A-Za-z]{2,6}$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REG_EXP);

    public static boolean isMatch(String context) {
        return PATTERN.matcher(context).matches();
    }
}
