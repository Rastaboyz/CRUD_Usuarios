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
        if (tipoAccion.contains("Añadir")){
            String rol = request.getParameter("nombreRol");
            String descripcion = request.getParameter("descripcionRol");
            result = DAO.anadirRol(rol, descripcion);
        }else if (tipoAccion.contains("Actualizar")){
            String rol = request.getParameter("nombreRol");
            String nuevoRol = request.getParameter("rolNuevo");
            String nuevoDescripcion = request.getParameter("descNuevo");
            result = DAO.modificarRol(rol, nuevoRol, nuevoDescripcion);
        }else if (tipoAccion.contains("Borrar")){
            String rol = request.getParameter("nombreRol");
            result = DAO.borrarRol(rol);
        }
        request.setAttribute("insertResult", result); //Guarda como atributo en la URL.
        request.getRequestDispatcher("/ControllerServlet").forward(request, response);
    }
}
