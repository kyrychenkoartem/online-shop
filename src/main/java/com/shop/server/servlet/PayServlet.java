package com.shop.server.servlet;

import com.shop.server.exception.ValidationException;
import com.shop.server.model.dto.bank_card.BankCardRequest;
import com.shop.server.model.dto.delivery_address.DeliveryAddressRequest;
import com.shop.server.service.PayService;
import com.shop.server.utils.CookieUtils;
import com.shop.server.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.shop.server.utils.CookiesHelper.ERRORS;
import static com.shop.server.utils.CookiesHelper.ORDER;
import static com.shop.server.utils.UrlPath.LOGIN;
import static com.shop.server.utils.UrlPath.PAY;
import static com.shop.server.utils.UrlPath.PRODUCTS;

@WebServlet(PAY)
public class PayServlet extends HttpServlet {

    private final PayService payService = PayService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.get("success"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (CookieUtils.isUserCookieNotEmpty(req)) {
            var userId = CookieUtils.getUser(req);
            if (CookieUtils.isOrderCookieNotEmpty(req)) {
                var addressRequest = DeliveryAddressRequest.builder()
                        .address(req.getParameter("address"))
                        .city(req.getParameter("city"))
                        .province(req.getParameter("province"))
                        .postalCode(req.getParameter("postal_code"))
                        .build();
                var cardRequest = BankCardRequest.builder()
                        .userId(userId)
                        .cardNumber(req.getParameter("card_number"))
                        .expiryDate(req.getParameter("expiry_date"))
                        .bank(req.getParameter("bank"))
                        .cvv(req.getParameter("cvv"))
                        .cardType(req.getParameter("card"))
                        .build();
                var orderId = CookieUtils.getOrderId(req);
                try {
                    payService.finishPayment(orderId, userId, addressRequest, cardRequest);
                    req.setAttribute(ORDER, orderId);
                    CookieUtils.resetCookie(req);
                    doGet(req, resp);
                } catch (ValidationException exception) {
                    req.setAttribute(ERRORS, exception.getErrors());
                    req.getRequestDispatcher(JspHelper.get("order"))
                            .forward(req, resp);
                }
            } else {
                resp.sendRedirect(PRODUCTS);
            }
        } else {
            resp.sendRedirect(LOGIN);
        }

    }
}
