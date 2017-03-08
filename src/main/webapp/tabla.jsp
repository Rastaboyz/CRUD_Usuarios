<%--
  Created by IntelliJ IDEA.
  User: Budha
  Date: 16/2/17
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Lista</title>
    <link type="icon"/>
</head>
<body>
<h2>Welcome ${requestScope.username} </h2>
<!-- request.request.request.userPrincipal.name -->
<h3>Lista usuarios</h3>
<!-- Llama al tag rellenando el HTML que se haya definido allí. -->
<tags:tablaUsuarios users='${requestScope.listaUsuarios}'/> <!-- .listaUsuarios es la KEY del servlet -->
<tags:admin isAdmin='${requestScope.isAdmin}'/>
<button><a href="logout.jsp" >Cerrar sesión</a></button>
</body>
</html>
