package com.shop.server.validator.impl;

import com.shop.server.matcher.BankCardMatcher;
import com.shop.server.matcher.CvvCardMatcher;
import com.shop.server.model.dto.bank_card.BankCardRequest;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ExpiryDateFormatter;
import com.shop.server.validator.Error;
import com.shop.server.validator.ValidationResult;
import com.shop.server.validator.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardRequestValidator implements Validator<BankCardRequest> {

    private static final BankCardRequestValidator INSTANCE = new BankCardRequestValidator();

    @Override
    public ValidationResult isValid(BankCardRequest bankCardRequest) {
        var validationResult = new ValidationResult();
        if (!BankCardMatcher.isMatch(bankCardRequest.getCardNumber())) {
            validationResult.add(Error.of(ErrorResponseStatusType.ILLEGAL_CARD_NUMBER_FORMAT_EXCEPTION, bankCardRequest.getCardNumber()));
        }
        if (!ExpiryDateFormatter.isMatch(bankCardRequest.getExpiryDate())) {
            validationResult.add(Error.of(ErrorResponseStatusType.ILLEGAL_CARD_EXPIRY_DATE_FORMAT_EXCEPTION, bankCardRequest.getExpiryDate()));
        }
        if (bankCardRequest.getBank() == null) {
            validationResult.add(Error.of(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, "cvv can't be blank"));
        }
        if (!CvvCardMatcher.isMatch(bankCardRequest.getCvv())) {
            validationResult.add(Error.of(ErrorResponseStatusType.ILLEGAL_CARD_CVV_FORMAT_EXCEPTION, bankCardRequest.getCvv()));
        }
        return validationResult;
    }

    public static BankCardRequestValidator getInstance() {
        return INSTANCE;
    }
}
