<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kromarong
  Date: 24.10.2019
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customers</title>
</head>
<body>
<form action="${action}" method="post">
    <input type="hidden" name="id" id="id" value="${customer.id}">
    <p>
        <label for="name">Name</label>
        <input type="text" id="name" name="name" value="${customer.name}" />
    </p>
    <input type="submit" />
</form>


<label>purchased products</label>
<table border="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
    </tr>

    <c:forEach items="${customer.products}" var="prod">
        <tr>
            <td>${prod.id}</td>
            <td>${prod.name}</td>
            <td>${prod.description}</td>
            <td>${prod.price}</td>
        </tr>
    </c:forEach>

    </table>

<br>
<label>products to buy</label>
<table border="1">
    <c:url value="/customers/buy" var="createUrl">
        <c:param name="customerId" value="${customer.id}"/>
        <c:param name="productId" value="${product.id}"/>
    </c:url>

    <c:forEach items="${products}" var="prod">
        <tr>
            <c:url value="/customers/buy" var="editUrl">
                <c:param name="productId" value="${prod.id}"/>
                <c:param name="customerId" value="${customer.id}"/>
            </c:url>
            <td>${prod.id}</td>
            <td><a href="${editUrl}">${prod.name}</a></td>
            <td>${prod.description}</td>
            <td>${prod.price}</td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
