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
public class UpdateUserController extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String nuevoUsuario = request.getParameter("nuevoUsuario");
        String nuevoPassword = request.getParameter("nuevoPassword");
        String nuevoRol = request.getParameter("rol");
        String result = DAO.modificarUsuario(usuario, nuevoUsuario, nuevoPassword, nuevoRol);
        request.setAttribute("updateResult", result); //Guarda como atributo en la URL.
        request.getRequestDispatcher("index.jsp").include(request, response); // Incluye nuevos cambios.
    }
}
