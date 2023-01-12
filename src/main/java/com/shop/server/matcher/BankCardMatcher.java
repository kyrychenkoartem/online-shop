package com.shop.server.matcher;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BankCardMatcher {

    private static final String BANK_CARD_REG_EXP = "\\b([0-9]{4})[0-9]{0,9}([0-9]{4})\\b";
    private static final Pattern PATTERN = Pattern.compile(BANK_CARD_REG_EXP);

    public static boolean isMatch(String context) {
        return PATTERN.matcher(context).matches();
    }
}
