<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <c:if test="${not empty sessionScope.user}">
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit">Logout</button>
        </form>
    </c:if>
    <c:if test="${ empty sessionScope.user}">
        <form action="${pageContext.request.contextPath}/login" method="get">
            <button type="submit">Login</button>
        </form>
    </c:if>
</div>