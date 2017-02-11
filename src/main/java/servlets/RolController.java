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
public class RolController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipoAccion = request.getParameter("accion");
        String result = "";
        if (tipoAccion.equalsIgnoreCase("Añadir")){
            String rol = request.getParameter("rolName");
            String descripcion = request.getParameter("rolDescription");
            result = DAO.anadirRol(rol, descripcion);
        }else if (tipoAccion.equalsIgnoreCase("actualizar")){
            String rol = request.getParameter("rolName");
            String nuevoRol = request.getParameter("nuevoRol");
            String nuevoDescripcion = request.getParameter("rolDescription");
            result = DAO.modificarRol(rol, nuevoRol, nuevoDescripcion);
        }else if (tipoAccion.equalsIgnoreCase("borrar")){
            String rol = request.getParameter("rolName");
            result = DAO.borrarRol(rol);
        }
        request.setAttribute("resGestion", result); //Guarda como atributo en la URL.
        request.getRequestDispatcher("index.jsp").include(request, response); // Incluye nuevos cambios.
    }
}
