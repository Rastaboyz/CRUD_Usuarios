<%@ page language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <link href="" rel="icon">
</head>
<body>
<h3>Lista usuarios</h3>
<!-- Llama al tag rellenando el HTML que se haya definido allí. -->
<tags:tablaUsuarios users='${requestScope.listaUsuarios}'/> <!-- .listaUsuarios es la KEY del servlet -->

<h1>Gestionar usuarios</h1>
<form action="/Usuarios" method="get">
    <input type="text" name="usuario" value="Pep" placeholder="Usuario">
    <input type="password" name="passwd" value="Pep" placeholder="Contraseña">
    <tags:listaRoles roles='${requestScope.listaRoles}'/> <!-- .listaRoles es la KEY del servlet -->
    <input type="submit" name="accion" value="Añadir usuario">
    <input type="submit" name="accion" value="Actualizar usuario">
    <input type="submit" name="accion" value="Borrar usuario">
</form>

<h1>Gestionar roles</h1>
<form action="/Roles" method="get">
    <input type="text" name="rolName" value="admin" placeholder="rolName">
    <input type="text" name="rolName" value="adminNuevo" placeholder="nuevoRol">
    <input type="text" name="rolDescription" value="rol administrador secundario" placeholder="rolDescription">
    <input type="submit" name="accion" value="Añadir rol">
    <input type="submit" name="accion" value="Actualizar rol">
    <input type="submit" name="accion" value="Borrar rol">
</form>

<div id="resGestion">${requestScope.insertResult}</div>
<!-- OLD
<h3>Insertar usuario</h3>
<form action="/AddUserController" method="get">
    <input type="text" name="usuario" value="Pep" placeholder="Usuario">
    <input type="password" name="passwd" value="Pep" placeholder="Contraseña">
    <!tags:listaRoles roles='$*{requestScope.listaRoles}'/>
    <input type="submit" value="Añadir usuario">
</form>
<div id="resultInsert">$*{requestScope.insertResult}</div>

<h3>Borrar usuario</h3>
<form action="/DeleteUserController" method="get">
    <input type="text" name="usuario" value="Pep" placeholder="Usuario">
    <input type="submit" value="Borrar usuario">
</form>
<div id="resultDelete">$*{requestScope.deleteResult}</div>

<h3>Actualizar usuario</h3>
<form action="/UpdateUserController" method="get">
    <input id="nombreUsuario" onblur="activarBoton()" type="text" name="usuario" value="Pep" placeholder="Usuario">
    <input type="text" name="nuevoUsuario" value="Pep2" placeholder="Usuario">
    <input type="password" name="nuevoPassword" value="Pep2" placeholder="Usuario">
    <!tags:listaRoles roles='$*{requestScope.listaRoles}'/>
    <input id="submit" type="submit" disabled value="Actualizar usuario">
</form>
<div id="resultUpdate">$*{requestScope.updateResult}</div>

<h3>Insertar Rol</h3>
<form action="/AddRolController" method="get">
    <input type="text" name="rolName" value="admin2" placeholder="rolName">
    <input type="text" name="rolDescription" value="rol administrador secundario" placeholder="rolDescription">
    <input type="submit" value="Insertar rol">
</form>
<div id="resultRolInsert">$*{requestScope.insertRolResult}</div>

<h3>Borrar Rol</h3>
<form action="/DeleteRolController" method="get">
    <input type="text" name="rolName" value="admin2" placeholder="rolName">
    <input type="submit" value="Borrar rol">
</form>
<div id="resultRolDelete">$*{requestScope.deleteRolResult}</div>

<h3>Modificar Rol</h3>
<form action="/UpdateRolController" method="get">
    <input id="rolName" type="text" name="rolName" value="admin" placeholder="Rol">
    <input type="text" name="nuevoRolName" value="admin2" placeholder="rolName">
    <input type="text" name="nuevoRolDescription" value="rol modificado" placeholder="rolDescription">
    <input class="submit2" type="submit" value="Borrar rol" >
</form>
<div id="resultRolDelete">$*{requestScope.updateRolResult}</div>
-->


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
