package com.shop.server.utils;

public final class JspHelper {

    private static final String JSP_FORMAT = "/WEB-INF/jsp/%s.jsp";

    private JspHelper() {
    }

    public static String get(String jspName) {
        return String.format(JSP_FORMAT, jspName);
    }
}
