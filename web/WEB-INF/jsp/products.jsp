<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Products</title>
</head>
<body>
<%@ include file="header.jsp"%>
<h1>Products:</h1>
    <form action="${pageContext.request.contextPath}/cart?cartId=${requestScope.cartId}" >
        <button type="submit">Go to cart</button>
    </form>
<c:forEach var="product" items="${requestScope.products}">
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/products?productId=${product.id}">${product.name}</a>
            <br>
            <form action="${pageContext.request.contextPath}/cart?productId=${product.id}" method="post">
                <label for="quantities">Quantities:
                    <input type="number" min="1" name="quantities" id="quantities">
                </label><br>
                <button type="submit">Add to cart</button>
            </form>
            <br>
        </li>
    </ul>
</c:forEach>
</body>
</html>

