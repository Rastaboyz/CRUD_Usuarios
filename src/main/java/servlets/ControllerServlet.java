package servlets;

import clases.DAO;
import clases.pojo.Roles;
import clases.pojo.Usuarios;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vitaly94 on 14/12/2016.
 */
public class ControllerServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Usuarios> listaUsuarios = DAO.getAllUsers();
        ArrayList<Roles> listaRoles = DAO.getAllRols();
        request.setAttribute("listaUsuarios", listaUsuarios); //Guarda como atributo en la URL.
        request.setAttribute("listaRoles", listaRoles); //Guarda como atributo en la URL.
        request.getRequestDispatcher("index.jsp").forward(request, response); // Carga pagina entera.
    }
}
