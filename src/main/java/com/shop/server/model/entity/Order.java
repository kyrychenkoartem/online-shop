package com.shop.server.model.entity;

import com.shop.server.model.type.OrderStatus;
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
public class Order {

    private Long id;
    private Cart cart;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private OrderStatus status;
}
