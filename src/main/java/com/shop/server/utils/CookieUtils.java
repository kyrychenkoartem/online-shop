package com.shop.server.utils;

import com.shop.server.model.dto.cart.CartResponse;
import com.shop.server.model.dto.order.OrderResponse;
import com.shop.server.model.dto.user.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CookieUtils {

    public static void setCartCookie(CartResponse cartResponse, HttpServletRequest req) {
        req.getSession().setAttribute("cartId", cartResponse.getId());
    }

    public static void setCartCookie(Long cartId, HttpServletRequest req) {
        req.getSession().setAttribute("cartId", cartId);
    }

    public static void setUserCookie(UserResponse user, HttpServletRequest req) {
        req.getSession().setAttribute("user", user);
    }

    public static void setOrderCookie(OrderResponse order, HttpServletRequest req) {
        req.getSession().setAttribute("orderId", order.getId());
    }

    public static boolean isCartIdCookieNotEmpty(HttpServletRequest req) {
        var cartId = (Long) req.getSession().getAttribute("cartId");
        return cartId != null;
    }

    public static boolean isUserCookieNotEmpty(HttpServletRequest req) {
        var user = (UserResponse) req.getSession().getAttribute("user");
        return user != null;
    }

    public static boolean isOrderCookieNotEmpty(HttpServletRequest req) {
        var orderId = (Long) req.getSession().getAttribute("orderId");
        return orderId != null;
    }

    public static Long getUser(HttpServletRequest req) {
        var user = (UserResponse) req.getSession().getAttribute("user");
        return user.getId();
    }

    public static Long getCartId(HttpServletRequest req) {
        return (Long) req.getSession().getAttribute("cartId");
    }

    public static Long getOrderId(HttpServletRequest req) {
        return (Long) req.getSession().getAttribute("orderId");
    }

    public static void resetCookie(HttpServletRequest req) {
        var user = (UserResponse) req.getSession().getAttribute("user");
        req.getSession().invalidate();
        req.getSession().setAttribute("user", user);
    }
}
