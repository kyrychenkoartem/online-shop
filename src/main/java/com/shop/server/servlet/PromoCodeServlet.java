package com.shop.server.servlet;

import com.shop.server.exception.CartNotFoundException;
import com.shop.server.service.CartService;
import com.shop.server.utils.CookieUtils;
import com.shop.server.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.commons.lang3.ObjectUtils;

import static com.shop.server.utils.UrlPath.CART;
import static com.shop.server.utils.UrlPath.PROMO_CODE;

@WebServlet(PROMO_CODE)
public class PromoCodeServlet extends HttpServlet {

    private final CartService cartService = CartService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ObjectUtils.isNotEmpty(req.getParameter("promoCode"))) {
            var promoCode = req.getParameter("promoCode");
            if (CookieUtils.isCartIdCookieNotEmpty(req)) {
                try {
                    var cartId = CookieUtils.getCartId(req);
                    cartService.addPromoCode(cartId, promoCode);
                    resp.sendRedirect(CART);
                } catch (CartNotFoundException exception) {
                    req.getRequestDispatcher(JspHelper.get("cart"))
                            .forward(req, resp);
                }
            }
        } else {
            resp.sendRedirect(CART);
        }
    }
}
