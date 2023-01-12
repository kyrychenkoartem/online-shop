<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Order</title>
</head>
<body>
<%@ include file="header.jsp"%>
<c:forEach var="profuctItem" items="${requestScope.order.cart.productItems}">
    <ul>
        <li>
            Product: ${profuctItem.product.name} Count: ${profuctItem.count}<br>
        </li>
    </ul>
</c:forEach>
Subtotal: ${requestScope.order.cart.price} $<br>
Promo code: ${requestScope.order.cart.promoCode.value} <br> <br>

<div>Shipping Information</div>
<br>
<form action="/pay" method="post">
    <label for="address">Address:
        <input type="text" name="address" id="address"/>
    </label><br>
    <label for="city">City:
        <input type="text" name="city" id="city"/>
    </label><br>
    <label for="province">Province:
        <input type="text" name="province" id="province"/>
    </label><br>
    <label for="postal_code">Postal code:
        <input type="text" name="postal_code" id="postal_code"/>
    </label><br><br>

<div>Payment information</div>
<br>

    <label for="card_number">Card number:
        <input type="text" name="card_number" id="card_number"/>
    </label><br>
    <label for="expiry_date">Expiry date: Format type MM/YY
        <input type="text" name="expiry_date" id="expiry_date"/>
    </label><br>
    <label for="bank">Bank:
        <input type="text" name="bank" id="bank"/>
    </label><br>
    <label for="cvv">CVV:
        <input type="text" name="cvv" id="cvv"/>
    </label><br>
    <select name="card" id="card">
        <c:forEach var="card" items="${requestScope.cards}">
            <option value="${card}">${card}</option>
        </c:forEach>
    </select><br>
    <button type="submit">Pay</button>
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
