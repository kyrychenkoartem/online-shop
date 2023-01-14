package com.shop.server.mapper;

import com.shop.server.dao.CartDao;
import com.shop.server.dao.UserDao;
import com.shop.server.exception.NotFoundException;
import com.shop.server.mapper.extractor.CartExtractor;
import com.shop.server.mapper.extractor.UserExtractor;
import com.shop.server.model.dto.order.OrderResponse;
import com.shop.server.model.entity.Order;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.model.type.OrderStatus;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper {

    private static final OrderMapper INSTANCE = new OrderMapper();
    private final CartDao cartDao = CartDao.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CartExtractor cartExtractor = CartExtractor.getExtractor();
    private final UserExtractor userExtractor = UserExtractor.getExtractor();

    public Order toEntity(Long cartId, Long userId) {
        if (ObjectUtils.isEmpty(cartId) && ObjectUtils.isEmpty(userId)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENTS_NOT_FOUND_EXCEPTION);
        }
        var cart = cartDao.findById(cartId, cartExtractor);
        var user = userDao.findById(userId, userExtractor);
        return Order.builder()
                .cart(cart.get())
                .user(user.get())
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.PROCESSING)
                .build();
    }

    public OrderResponse toResponse(Order order) {
        if (ObjectUtils.isEmpty(order)) {
            throw new NotFoundException(ErrorResponseStatusType.ORDER_NOT_FOUND_EXCEPTION, order);
        }
        return OrderResponse.builder()
                .id(order.getId())
                .cart(order.getCart())
                .user(order.getUser())
                .status(order.getStatus())
                .build();
    }

    public static OrderMapper getInstance() {
        return INSTANCE;
    }
}
