package com.shop.server.filter;

import com.shop.server.model.dto.user.UserResponse;
import com.shop.server.utils.CookieUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.shop.server.utils.UrlPath.ALL_PATHS;
import static com.shop.server.utils.UrlPath.CART;
import static com.shop.server.utils.UrlPath.LOGIN;
import static com.shop.server.utils.UrlPath.PRODUCTS;
import static com.shop.server.utils.UrlPath.PROMO_CODE;
import static com.shop.server.utils.UrlPath.REGISTRATION;

@WebFilter(ALL_PATHS)
public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_PATH = Set.of(LOGIN, REGISTRATION, PRODUCTS, PROMO_CODE, CART);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (isUserLoggedIn(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (isPublicPath(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            var prevPage = ((HttpServletRequest) servletRequest).getHeader("referer");
            var userCookieNotEmpty = CookieUtils.isUserCookieNotEmpty(((HttpServletRequest) servletRequest));
            ((HttpServletRequest) servletRequest).getSession().setAttribute("targetPage", uri);
            ((HttpServletResponse) servletResponse).sendRedirect(!userCookieNotEmpty ? LOGIN : prevPage);
        }
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        var user = (UserResponse) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user != null;
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }

    private boolean isCartIdAvailable(ServletRequest servletRequest) {
        var cartId = (Long) ((HttpServletRequest) servletRequest).getSession().getAttribute("cartId");
        return cartId != null;
    }
}
