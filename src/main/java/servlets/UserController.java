package servlets;

import clases.DAO;
import clases.pojo.Usuarios;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vitaly94 on 09/02/2017.
 */
public class UserController extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipoAccion = request.getParameter("accion");
        String result = "";
        if (tipoAccion.contains("Añadir")){
            String usuario = request.getParameter("usuario");
            String passwd = request.getParameter("password");
            String rol = request.getParameter("rol");
            result = DAO.anadirUsuario(usuario, passwd, rol);
        }else if (tipoAccion.contains("Actualizar")){
            String usuario = request.getParameter("usuario");
            //String nuevoUsuario = request.getParameter("usuarioNuevo");
            String passwdNuevo = request.getParameter("passwdNuevo");
            String rolNuevo = request.getParameter("nuevoRol");
            result = DAO.modificarUsuario(usuario, passwdNuevo, rolNuevo);
        }else if (tipoAccion.contains("Borrar")){
            String usuario = request.getParameter("usuario");
            result = DAO.borrarUsuario(usuario);
        }
        request.setAttribute("insertResult", result); //Guarda como atributo en la URL.
        request.getRequestDispatcher("/ControllerServlet").forward(request, response);

    }
}
