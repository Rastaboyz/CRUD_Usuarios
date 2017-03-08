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

        try {
            if(request.getParameter("formulario") != null || request.getParameter("usuario") != null || request.getParameter("nombreRol") != null){
                ArrayList<Roles> listaRoles = DAO.getAllRols();
                request.setAttribute("listaRoles", listaRoles); //Guarda como atributo en la URL.
                request.getRequestDispatcher("index.jsp").forward(request, response); // Carga pagina entera.
            }

            request.getUserPrincipal();
            ArrayList<Usuarios> listaUsuarios = DAO.getAllUsers();
            String isAdmin = "0";
            String username = request.getUserPrincipal().getName();
            if(request.isUserInRole("admin")){
                isAdmin = "1";
            }
            request.setAttribute("listaUsuarios", listaUsuarios); //Guarda como atributo en la URL.
            request.setAttribute("username", username); //Guarda como atributo en la URL.
            request.setAttribute("isAdmin", isAdmin); // Verificar que el usuario logeado es administrador.
            request.getRequestDispatcher("tabla.jsp").forward(request, response); // Carga pagina entera.
        }catch (Exception ex){
            System.out.println("Error general: " + ex.getMessage());
        }
    }
}
