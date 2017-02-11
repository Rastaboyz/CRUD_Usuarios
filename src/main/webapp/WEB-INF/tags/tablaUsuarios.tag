<!-- Creación de librería de tipo TAG -->
<!-- Este tag crea una "variable" para asignar valores (en este caso de tipo LISTA)
y lo que obtenga esta variable, luego se usa para pintar en la página HTML (VISTA de MVC).

NOTA: Si no se especifica el "type" hace referencia al tipo STRING -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="users" required="true" type="java.util.List"%>

<!-- En "localhost" la lista esta vacía, por tanto se usa el IF para evitar mostrar tabla vacía. -->
<c:if test="${users != null}">

    <table style="border: 1px solid; border-collapse: collapse;">
        <th style="border: 1px solid;">Nombre Usuario</th>
        <th style="border: 1px solid;">Contraseña</th>
        <c:forEach items="${users}" var="user">
            <tr>
                <td style="border: 1px solid;">${user.nombreUsuario}</td>
                <td style="border: 1px solid;">${user.password}</td>
            </tr>
        </c:forEach>
    </table>

</c:if>