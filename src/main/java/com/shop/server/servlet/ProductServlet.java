package com.shop.server.servlet;

import com.shop.server.service.ProductService;
import com.shop.server.utils.CookieUtils;
import com.shop.server.utils.JspHelper;
import com.shop.server.utils.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.apache.commons.lang3.ObjectUtils;

@WebServlet(UrlPath.PRODUCTS)
public class ProductServlet extends HttpServlet {

    ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productService.getAll());
        if (CookieUtils.isCartIdCookieNotEmpty(req)) {
            var cartId = CookieUtils.getCartId(req);
            req.setAttribute("cartId", cartId);
        }
        if (ObjectUtils.isNotEmpty(req.getParameter("productId"))) {
            var productId = Optional.of(Long.valueOf(req.getParameter("productId")));
            req.setAttribute("product", productService.getById(productId.get()));
            req.getRequestDispatcher(JspHelper.get("product"))
                    .forward(req, resp);
        } else {
            req.getRequestDispatcher(JspHelper.get("products"))
                    .forward(req, resp);
        }
    }

}
