<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="isAdmin" required="true"%>

<c:if test="${isAdmin == '1'}">
    <form action="/ControllerServlet" method="get">
        <input type="submit" name="formulario" value="Editar Usuarios y Roles">
    </form>
</c:if>