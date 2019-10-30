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
        <label for="minPriceFilter">Price filter</label>
        <select id="minPriceFilter" name="price">
            <option value="all">all</option>
            <option value="min">min</option>
            <option value="max">max</option>
            <option value="min_max">min_max</option>
        </select>
        <input type="submit" value="Apply" />
    </form>

    <c:forEach items="${products}" var="prod">
        <tr>
            <td>${prod.id}</td>

            <td>${prod.name}</td>

            <td>${prod.description}</td>

            <td>${prod.category.name}</td>

            <td>${prod.price}</td>

        </tr>
    </c:forEach>

</table>

</body>

</html>
