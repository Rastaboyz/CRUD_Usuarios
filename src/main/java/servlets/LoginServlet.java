package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Budha on 11/2/17.
 */
public class LoginServlet extends HttpServlet{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String userName = request.getParameter("txtUserName");
            String password = request.getParameter("txtPassword");

            out.println("Before Login" + "<br><br>");
            out.println("IsUserInRole?.."
                    + request.isUserInRole("javaee6user") + "<br>");
            out.println("getRemoteUser?.." + request.getRemoteUser() + "<br>");
            out.println("getUserPrincipal?.."
                    + request.getUserPrincipal() + "<br>");
            out.println("getAuthType?.." + request.getAuthType() + "<br><br>");

            try {
                request.login(userName, password);
            } catch (ServletException ex) {
                out.println("Login Failed with a ServletException.."
                        + ex.getMessage());
                return;
            }
            out.println("After Login..." + "<br><br>");
            out.println("IsUserInRole?.."
                    + request.isUserInRole("javaee6user") + "<br>");
            out.println("getRemoteUser?.." + request.getRemoteUser() + "<br>");
            out.println("getUserPrincipal?.."
                    + request.getUserPrincipal() + "<br>");
            out.println("getAuthType?.." + request.getAuthType() + "<br><br>");

            request.logout();
            out.println("After Logout..." + "<br><br>");
            out.println("IsUserInRole?.."
                    + request.isUserInRole("javaee6user") + "<br>");
            out.println("getRemoteUser?.." + request.getRemoteUser() + "<br>");
            out.println("getUserPrincipal?.."
                    + request.getUserPrincipal() + "<br>");
            out.println("getAuthType?.." + request.getAuthType() + "<br>");
        } finally {
            out.close();
        }
    }
}
