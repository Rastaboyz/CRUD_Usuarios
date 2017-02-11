package servlets;

import clases.DAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vitaly94 on 09/02/2017.
 */
public class UserController extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipoAccion = request.getParameter("accion");
        String result = "";
        if (tipoAccion.contains("Añadir")){
            String nombre = request.getParameter("usuario");
            String passwd = request.getParameter("passwd");
            String rol = request.getParameter("rol");
            result = DAO.anadirUsuario(nombre, passwd, rol);
        }else if (tipoAccion.contains("actualizar")){
            String usuario = request.getParameter("usuario");
            String nuevoUsuario = request.getParameter("nuevoUsuario");
            String nuevoPassword = request.getParameter("nuevoPassword");
            String nuevoRol = request.getParameter("rol");
            result = DAO.modificarUsuario(usuario, nuevoUsuario, nuevoPassword, nuevoRol);
        }else if (tipoAccion.contains("borrar")){
            String usuario = request.getParameter("usuario");
            result = DAO.borrarUsuario(usuario);
        }
        request.setAttribute("resGestion", result); //Guarda como atributo en la URL.
        request.getRequestDispatcher("index.jsp").include(request, response); // Incluye nuevos cambios.
    }
}
