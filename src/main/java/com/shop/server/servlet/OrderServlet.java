package com.shop.server.servlet;

import com.shop.server.model.type.CardType;
import com.shop.server.service.OrderService;
import com.shop.server.utils.CookieUtils;
import com.shop.server.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.shop.server.utils.UrlPath.LOGIN;
import static com.shop.server.utils.UrlPath.ORDER;
import static com.shop.server.utils.UrlPath.PRODUCTS;

@WebServlet(ORDER)
public class OrderServlet extends HttpServlet {
    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cards", CardType.values());
        req.getRequestDispatcher(JspHelper.get("order"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (CookieUtils.isUserCookieNotEmpty(req)) {
            var user = CookieUtils.getUser(req);
            if (CookieUtils.isCartIdCookieNotEmpty(req)) {
                var cartId = CookieUtils.getCartId(req);
                var order = orderService.save(cartId, user);
                CookieUtils.setOrderCookie(order, req);
                req.setAttribute("order", order);
                doGet(req, resp);
            } else {
                resp.sendRedirect(PRODUCTS);
            }
        } else {
            resp.sendRedirect(LOGIN);
        }
    }
}
