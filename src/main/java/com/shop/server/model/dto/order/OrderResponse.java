package com.shop.server.model.dto.order;

import com.shop.server.model.entity.Cart;
import com.shop.server.model.entity.User;
import com.shop.server.model.type.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class OrderResponse {

    private Long id;
    private Cart cart;
    private User user;
    private OrderStatus status;
}
