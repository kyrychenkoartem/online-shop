package com.shop.server.servlet;

import com.shop.server.exception.ProductNotEnoughException;
import com.shop.server.service.CartService;
import com.shop.server.utils.CookieUtils;
import com.shop.server.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.apache.commons.lang3.ObjectUtils;

import static com.shop.server.utils.UrlPath.CART;
import static com.shop.server.utils.UrlPath.PRODUCTS;

@WebServlet(CART)
public class CartServlet extends HttpServlet {

    private final CartService cartService = CartService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (CookieUtils.isCartIdCookieNotEmpty(req)) {
            var cartId = CookieUtils.getCartId(req);
            req.setAttribute("cartResponse", cartService.findById(cartId));
            req.getRequestDispatcher(JspHelper.get("cart"))
                    .forward(req, resp);
        } else {
            resp.sendRedirect(PRODUCTS);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ObjectUtils.isNotEmpty(req.getParameter("quantities"))) {
            var quantities = Integer.valueOf(req.getParameter("quantities"));
            var productId = Optional.of(Long.valueOf(req.getParameter("productId")));
            if (CookieUtils.isCartIdCookieNotEmpty(req)) {
                var cartId = CookieUtils.getCartId(req);
                try {
                    cartService.addToCart(cartId, productId.get(), quantities);
                    CookieUtils.setCartCookie(cartId, req);
                    resp.sendRedirect(PRODUCTS);
                } catch (ProductNotEnoughException exception) {
                    req.getRequestDispatcher(JspHelper.get("products"))
                            .forward(req, resp);
                }
            } else {
                try {
                    var cartResponse = cartService.save(productId.get(), quantities);
                    CookieUtils.setCartCookie(cartResponse, req);
                    resp.sendRedirect(PRODUCTS);
                } catch (ProductNotEnoughException exception) {
                    req.getRequestDispatcher(JspHelper.get("products"))
                            .forward(req, resp);
                }
            }
        }
    }

}
