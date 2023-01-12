<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<%@ include file="header.jsp"%>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="username">Username:
        <input type="text" name="username" id="username">
    </label><br>
    <label for="email">Email:
        <input type="text" name="email" id="email">
    </label><br>
    <label for="password">Password:
        <input type="password" name="password" id="password">
    </label><br>
    <label for="birthDate">Birthday:
        <input type="date" name="birthDate" id="birthDate">
    </label><br>
    <label for="firstName">First name:
        <input type="text" name="firstName" id="firstName">
    </label><br>
    <label for="lastName">Last name:
        <input type="text" name="lastName" id="lastName">
    </label><br>
    <select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option value="${role}">${role}</option>
        </c:forEach>
    </select><br>
    <select name="gender" id="gender">
        <c:forEach var="gender" items="${requestScope.genders}">
            <option value="${gender}">${gender}</option>
        </c:forEach>
    </select><br>
    <button type="submit">Send</button>
    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span> <br>
            </c:forEach>
        </div>
    </c:if>
</form>
</body>
</html>

