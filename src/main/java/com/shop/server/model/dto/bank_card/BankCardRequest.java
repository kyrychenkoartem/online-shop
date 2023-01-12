package com.shop.server.model.dto.bank_card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankCardRequest {

    private Long userId;
    private String cardNumber;
    private String expiryDate;
    private String bank;
    private String cvv;
    private String cardType;
}
