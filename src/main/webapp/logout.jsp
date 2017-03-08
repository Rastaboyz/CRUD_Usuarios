<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout</title>
    <link type="icon"/>
</head>
<body>
<%
    session.invalidate();
    response.sendRedirect("http://localhost:8080");
%>
</body>
</html>
