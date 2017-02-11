<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="roles" required="true" type="java.util.List"%>

<!-- En "localhost" la lista esta vacía, por tanto se usa el IF para evitar mostrar tabla vacía. -->
<c:if test="${roles != null}">

    <select name="rol">
        <c:forEach items="${roles}" var="rol">
            <option>
                ${rol.nombreRol}
            </option>
        </c:forEach>
    </select>

</c:if>