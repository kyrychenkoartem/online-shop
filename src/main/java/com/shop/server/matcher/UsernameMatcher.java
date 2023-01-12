package com.shop.server.matcher;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsernameMatcher {

    private static final String USERNAME_REG_EXP = "^[a-zA-Z0-9]{5,64}$";
    private static final Pattern PATTERN = Pattern.compile(USERNAME_REG_EXP);

    public static boolean isMatch(String context) {
        return PATTERN.matcher(context).matches();
    }
}
