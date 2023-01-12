package com.shop.server.servlet;

import com.shop.server.exception.ValidationException;
import com.shop.server.model.dto.user.UserRegistrationRequest;
import com.shop.server.model.type.Gender;
import com.shop.server.model.type.Role;
import com.shop.server.service.UserService;
import com.shop.server.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.shop.server.utils.UrlPath.LOGIN;
import static com.shop.server.utils.UrlPath.REGISTRATION;

@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(JspHelper.get("registration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userRegistrationRequest = UserRegistrationRequest.builder()
                .username(req.getParameter("username"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .birthDate(req.getParameter("birthDate"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .role(req.getParameter("role"))
                .gender(req.getParameter("gender"))
                .build();
        try {
            userService.save(userRegistrationRequest);
            resp.sendRedirect(LOGIN);
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
