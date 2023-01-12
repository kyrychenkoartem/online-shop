package com.shop.server.matcher;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CvvCardMatcher {

    private static final String BANK_CARD_REG_EXP = "^[0-9]{3,4}$";
    private static final Pattern PATTERN = Pattern.compile(BANK_CARD_REG_EXP);

    public static boolean isMatch(String context) {
        return PATTERN.matcher(context).matches();
    }
}
