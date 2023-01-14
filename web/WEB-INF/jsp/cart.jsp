<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<%@ include file="header.jsp"%>
<h1>Added Products:</h1>

<c:forEach var="profuctItem" items="${requestScope.cartResponse.productItem}">
    <ul>
        <li>
               Product: ${profuctItem.productResponse.name}    Count: ${profuctItem.count}<br>
        </li>
    </ul>
</c:forEach>
Subtotal: ${requestScope.cartResponse.price} $<br>
Promo code: ${requestScope.cartResponse.promoCode.value} <br>
<form action="/promo-code" method="post">
    <label for="promoCode">Promo code:
        <input type="text" name="promoCode" id="promoCode">
    </label><br>
    <button type="submit">Add promo code</button>
</form>
<form action="/products" method="get">
    <button type="submit">Back to products list</button>
</form>
<br>
<br>
<form action="/order" method="post">
    <button type="submit">Checkout</button>
</form>
</body>
</html>
