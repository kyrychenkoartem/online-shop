package com.shop.server;

import com.shop.server.dao.CartDao;
import com.shop.server.mapper.extractor.CartExtractor;
import com.shop.server.model.dto.cart.CartResponse;
import com.shop.server.model.entity.Cart;
import com.shop.server.service.CartService;
import java.util.List;
import java.util.Optional;

public class ApplicationRunner {
    public static void main(String[] args) {
        CartService instance = CartService.getInstance();
        CartResponse cartResponse = instance.deleteFromCart(1L, 8L);
        System.out.println(cartResponse);
    }
}
