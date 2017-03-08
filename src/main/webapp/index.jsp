<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <link type="icon"/>
</head>
<body>

<h1>Gestionar usuarios</h1>
<form action="/Usuarios" method="get">
    <input type="text" name="usuario" value="" placeholder="Usuario">
    <input type="password" name="password" value="" placeholder="Contraseña">
    <tags:listaRoles roles='${requestScope.listaRoles}'/> <!-- .listaRoles es la KEY del servlet -->
    <br/>
    <input type="submit" name="accion" value="Añadir usuario">
    <input type="submit" name="accion" value="Borrar usuario">
    <p>Actualización de datos</p>
    <!--<input type="text" name="usuarioNuevo" placeholder="Nuevo usuario">-->
    <input type="password" name="passwdNuevo" placeholder="Nuevo password">
    <tags:nuevoRol roles='${requestScope.listaRoles}'/>
    <br/>
    <input type="submit" name="accion" value="Actualizar usuario">
</form>

<h1>Gestionar roles</h1>
<form action="/Roles" method="get">
    <input type="text" name="nombreRol" placeholder="Nombre de rol">
    <input type="text" name="descripcionRol" value="rol administrador secundario" placeholder="Descripcion del rol">
    <br/>
    <input type="submit" name="accion" value="Añadir rol">
    <input type="submit" name="accion" value="Borrar rol">
    <p>Actualización de datos</p>
    <input type="text" name="rolNuevo" placeholder="Nuevo rol">
    <input type="text" name="descNuevo" placeholder="Nueva descripcion del rol">
    <br />
    <input type="submit" name="accion" value="Actualizar rol">
</form>

<form action="/ControllerServlet" method="get">
    <input type="submit" value="Tabla Usuarios">
</form>

<div id="resGestion">${requestScope.insertResult}</div>

<button><a href="logout.jsp" >Cerrar sesión</a></button>


<script>
    //controla el contenido del campo de usuario para activar o desactivar el boton de enviar
    function activarBoton() {
        if(document.getElementById("nombreUsuario").value != ""){
            document.getElementById("submit").removeAttribute("disabled");
        } else {
            document.getElementById("submit").disabled=true;
        }
    }
</script>

</body>
</html>
