package com.shop.server.model.entity;

import com.shop.server.model.type.PaymentStatus;
import java.time.LocalDateTime;
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
public class Payment {

    private Long id;
    private Order order;
    private BankCard bankCard;
    private LocalDateTime paymentTime;
    private PaymentStatus paymentStatus;
}
