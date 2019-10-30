<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>First Spring MVC View</title>
</head>
<body>
<c:forEach items="${msgList}" var="msg">
    <h1>${msg.message}</h1>
</c:forEach>
</body>
</html>
