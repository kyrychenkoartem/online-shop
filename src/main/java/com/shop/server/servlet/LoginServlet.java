package com.shop.server.servlet;

import com.shop.server.model.dto.user.UserResponse;
import com.shop.server.service.UserService;
import com.shop.server.utils.CookieUtils;
import com.shop.server.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;

import static com.shop.server.utils.UrlPath.ANY_LOGIN;
import static com.shop.server.utils.UrlPath.LOGIN;
import static com.shop.server.utils.UrlPath.LOGIN_ERROR_EMAIL;
import static com.shop.server.utils.UrlPath.PRODUCTS;

@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.get("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("email");
        var password = req.getParameter("password");
        userService.login(email, password)
                .ifPresentOrElse(user -> onLoginSuccess(user, req, resp),
                        () -> onLoginFail(req, resp)
                );
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect(LOGIN_ERROR_EMAIL + req.getParameter("email"));
    }

    @SneakyThrows
    private void onLoginSuccess(UserResponse user, HttpServletRequest req, HttpServletResponse resp) {
        CookieUtils.setUserCookie(user, req);
        var prevPage = req.getHeader("referer");
        if (ObjectUtils.isNotEmpty(req.getSession().getAttribute("targetPage"))) {
            req.getRequestDispatcher((String) req.getSession().getAttribute("targetPage"))
                    .forward(req, resp);
        } else if (prevPage.matches(ANY_LOGIN)) {
            resp.sendRedirect(PRODUCTS);
        } else {
            resp.sendRedirect(prevPage);
        }
    }


}
