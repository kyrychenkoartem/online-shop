package com.shop.server.model.entity;

import com.shop.server.model.type.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BankCard {

    private Long id;
    private User user;
    private String cardNumber;
    private String expiryDate;
    private String bank;
    private String cvv;
    private CardType cardType;
}
