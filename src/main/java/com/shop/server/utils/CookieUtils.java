package com.shop.server.utils;

import com.shop.server.model.dto.cart.CartResponse;
import com.shop.server.model.dto.order.OrderResponse;
import com.shop.server.model.dto.user.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import static com.shop.server.utils.CookiesHelper.CART_ID;
import static com.shop.server.utils.CookiesHelper.ORDER_ID;
import static com.shop.server.utils.CookiesHelper.USER;

@UtilityClass
public class CookieUtils {

    public static void setCartCookie(CartResponse cartResponse, HttpServletRequest req) {
        req.getSession().setAttribute(CART_ID, cartResponse.getId());
    }

    public static void setCartCookie(Long cartId, HttpServletRequest req) {
        req.getSession().setAttribute(CART_ID, cartId);
    }

    public static void setUserCookie(UserResponse user, HttpServletRequest req) {
        req.getSession().setAttribute(USER, user);
    }

    public static void setOrderCookie(OrderResponse order, HttpServletRequest req) {
        req.getSession().setAttribute(ORDER_ID, order.getId());
    }

    public static boolean isCartIdCookieNotEmpty(HttpServletRequest req) {
        var cartId = (Long) req.getSession().getAttribute(CART_ID);
        return cartId != null;
    }

    public static boolean isUserCookieNotEmpty(HttpServletRequest req) {
        var user = (UserResponse) req.getSession().getAttribute(USER);
        return user != null;
    }

    public static boolean isOrderCookieNotEmpty(HttpServletRequest req) {
        var orderId = (Long) req.getSession().getAttribute(ORDER_ID);
        return orderId != null;
    }

    public static Long getUser(HttpServletRequest req) {
        var user = (UserResponse) req.getSession().getAttribute(USER);
        return user.getId();
    }

    public static Long getCartId(HttpServletRequest req) {
        return (Long) req.getSession().getAttribute(CART_ID);
    }

    public static Long getOrderId(HttpServletRequest req) {
        return (Long) req.getSession().getAttribute(ORDER_ID);
    }

    public static void resetCookie(HttpServletRequest req) {
        var user = (UserResponse) req.getSession().getAttribute(USER);
        req.getSession().invalidate();
        req.getSession().setAttribute(USER, user);
    }
}
