<%--
  Created by IntelliJ IDEA.
  User: Budha
  Date: 11/2/17
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link type="icon"/>
</head>
<body>
<h2>Login</h2>
<form name="loginForm" method="POST" action="j_security_check">
    <p>User name: <input type="text" name="j_username" size="20"/></p>
    <p>Password: <input type="password" size="20" name="j_password"/></p>
    <p>  <input type="submit" value="Iniciar sesion"/></p>
</form>
</body>
</html>