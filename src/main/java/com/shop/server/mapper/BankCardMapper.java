package com.shop.server.mapper;

import com.shop.server.exception.NotFoundException;
import com.shop.server.model.dto.bank_card.BankCardRequest;
import com.shop.server.model.dto.bank_card.BankCardResponse;
import com.shop.server.model.entity.BankCard;
import com.shop.server.model.type.CardType;
import com.shop.server.model.type.ErrorResponseStatusType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardMapper {

    private static final BankCardMapper INSTANCE = new BankCardMapper();

    public static BankCardMapper getInstance() {
        return INSTANCE;
    }

    public BankCard toEntity(BankCardRequest cardRequest) {
        if (ObjectUtils.isEmpty(cardRequest)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, cardRequest);
        }
        return BankCard.builder()
                .cardNumber(cardRequest.getCardNumber())
                .expiryDate(cardRequest.getExpiryDate())
                .bank(cardRequest.getBank())
                .cvv(cardRequest.getCvv())
                .cardType(CardType.valueOf(cardRequest.getCardType()))
                .build();
    }

    public BankCardResponse toResponse(BankCard bankCard) {
        if (ObjectUtils.isEmpty(bankCard)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, bankCard);
        }
        return BankCardResponse.builder()
                .user(bankCard.getUser())
                .cardNumber(bankCard.getCardNumber())
                .bank(bankCard.getBank())
                .cardType(bankCard.getCardType())
                .build();
    }
}
