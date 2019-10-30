<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kromarong
  Date: 27.10.2019
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customers</title>
</head>
<body>

<a href="create">Create new customer</a>

<table border="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
    </tr>

    <c:forEach items="${customers}" var="customer">
        <tr>
            <c:url value="/customers/edit" var="editUrl">
                <c:param name="id" value="${customer.id}"/>
            </c:url>
            <td>${customer.id}</td>

            <td>
                <a href="${editUrl}">${customer.name}</a>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
