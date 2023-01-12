<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success</title>
</head>
<body>
<%@ include file="header.jsp"%>
    <h1>Thank you for your purchase!</h1><br>
    Your order number is : ${requestScope.order}<br><br>

<form action="/products" method="get">
    <button type="submit">Make a new order</button>
</form>
<br>
</body>
</html>
