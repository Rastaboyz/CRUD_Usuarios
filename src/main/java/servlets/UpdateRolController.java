package servlets;

import clases.DAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vitaly94 on 05/02/2017.
 */
public class UpdateRolController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rolName = request.getParameter("rolName");
        String nuevoRolName = request.getParameter("nuevoRolName");
        String nuevoRolDescription = request.getParameter("nuevoRolDescription");
        String result = DAO.modificarRol(rolName, nuevoRolName, nuevoRolDescription);
        request.setAttribute("updateRolResult", result); //Guarda como atributo en la URL.
        request.getRequestDispatcher("index.jsp").include(request, response); // Incluye nuevos cambios.
    }
}
