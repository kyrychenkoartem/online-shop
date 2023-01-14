<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Products</title>
</head>
<body>
<%@ include file="header.jsp"%>
<h1>Products ${requestScope.product.name}</h1>
<ul>
    ${requestScope.product.description} <br>
    ${requestScope.product.price} <br>
    ${requestScope.product.category} <br>
    ${requestScope.product.material} <br>
</ul>
</body>
</html>

