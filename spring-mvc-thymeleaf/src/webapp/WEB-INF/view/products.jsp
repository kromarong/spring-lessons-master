<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Products</title>
</head>

<body>

<table border="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Description</th>
        <th>Category</th>
    </tr>

    <form action="" method="get">
        <label for="categoryFilter">Category filter</label>
        <select id="categoryFilter" name="categoryId">
            <option value="${-1}" ${param['categoryId'] == null || param['categoryId'] == -1 ? 'selected' : ''}></option>
            <c:forEach items="${categories}" var="category">
                <option value="${category.id}" ${param['categoryId'] == category.id ? 'selected' : ''} >${category.name}</option>
            </c:forEach>
        </select>
        <input type="submit" value="Apply" />
    </form>

    <c:forEach items="${products}" var="prod">
        <tr>
            <td>${prod.id}</td>

            <c:url value="/products/edit" var="editProductUrl">
                <c:param name="id" value="${prod.id}"/>
            </c:url>
            <td><a href="${editProductUrl}">${prod.name}</a></td>

            <td>${prod.description}</td>

            <td>${prod.category.name}</td>
        </tr>
    </c:forEach>

</table>

</body>

</html>
