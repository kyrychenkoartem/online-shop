package com.shop.server.model.dto.bank_card;

import com.shop.server.model.entity.User;
import com.shop.server.model.type.CardType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class BankCardResponse {
    private User user;
    private String cardNumber;
    private String bank;
    private CardType cardType;
}
