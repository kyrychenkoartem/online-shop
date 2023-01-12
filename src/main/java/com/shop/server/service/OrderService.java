package com.shop.server.service;

import com.shop.server.dao.OrderDao;
import com.shop.server.exception.CartNotFoundException;
import com.shop.server.exception.NotFoundException;
import com.shop.server.mapper.OrderMapper;
import com.shop.server.mapper.extractor.OrderExtractor;
import com.shop.server.model.dto.order.OrderResponse;
import com.shop.server.model.type.ErrorResponseStatusType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private static final OrderService INSTANCE = new OrderService();
    private final OrderDao orderDao = OrderDao.getInstance();
    private final OrderExtractor orderExtractor = OrderExtractor.getExtractor();
    private final CartService cartService = CartService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final OrderMapper orderMapper = OrderMapper.getInstance();


    public static OrderService getInstance() {
        return INSTANCE;
    }

    public OrderResponse save(Long cartId, Long userId) {
        log.info("[save] invoked with cartId = [{}], and user = [{}]", cartId, userId);
        var cart = cartService.findById(cartId);
        if (ObjectUtils.isEmpty(cart)) {
            throw new CartNotFoundException(ErrorResponseStatusType.CART_NOT_FOUND_EXCEPTION, cartId);
        }
        var user = userService.getById(userId);
        if (ObjectUtils.isEmpty(user)) {
            throw new NotFoundException(ErrorResponseStatusType.USER_NOT_FOUND_EXCEPTION, userId);
        }
        var order = orderMapper.toEntity(cartId, userId);
        orderDao.save(order);
        return orderMapper.toResponse(order);
    }

    public OrderResponse findById(Long orderId) {
        log.info("[findById] invoked with orderId = [{}]", orderId);
        var optionalOrder = orderDao.findById(orderId, orderExtractor);
        if (optionalOrder.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.ORDER_NOT_FOUND_EXCEPTION, orderId);
        }
        return orderMapper.toResponse(optionalOrder.get());
    }
}
