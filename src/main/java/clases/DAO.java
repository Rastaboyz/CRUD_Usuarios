package clases;

import clases.pojo.Roles;
import clases.pojo.Usuarios;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by vitaly94 on 09/12/2016.
 */
public class DAO {
    // Variables globales.
    private static Integer filasAfectadas = 0;
    static ApplicationContext injection = new ClassPathXmlApplicationContext("/conf/spring.conf.xml");
    private static Statement stmt;
    private static Connection conn;
    private static ResultSet rs;

    public static void inicializarBBDD() {
        // Nombdre de Driver JDBC y la dirección URL para conectarse.
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        //final String DB_URL = "jdbc:mysql://172.16.12.151/dwes"; // Instituto
        //final String DB_URL = "jdbc:mysql://172.16.12.152/authorization"; // toni change bbdd insti
        final String DB_URL = "jdbc:mysql://192.168.1.133/dwes"; // Casa

        //  Credenciales para la conexión a base de datos.
        final String USER = "root";
        final String PASS = "test";

        stmt = null;
        conn = null;

        try {
            // Registra el driver de JDBC.
            Class.forName(JDBC_DRIVER);

            // Abre la conexión con la base de datos.
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException se) {
            // Error de base de datos.
            System.out.println("Error SQL");
            se.printStackTrace();
        } catch (Exception e) {
            // Error general.
            e.printStackTrace();
            System.out.println("Error general");
        } finally {

        } //end try
    }

    public static ArrayList<Usuarios> getAllUsers() {
        inicializarBBDD();
        try {
            // Ejecuta la consulta SQL.
            stmt = conn.createStatement();
            String sql = "SELECT * FROM users, user_roles, roles WHERE users.user_name = user_roles.user_name " +
                    "AND  user_roles.role_name = roles.role_name";
            rs = stmt.executeQuery(sql);

            // Inicializar lista de usuarios
            ArrayList<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
            // Extract data from result set
            while (rs.next()) {
                String user_name = rs.getString("users.user_name");
                String user_pass = rs.getString("users.user_pass");

                // Guarda al usuario en la lista de usuarios.
                Usuarios injectUsuarios = (Usuarios) injection.getBean("usuarios");
                injectUsuarios.setNombreUsuario(user_name);
                injectUsuarios.setPassword(user_pass);
                listaUsuarios.add(injectUsuarios);
            }
            // Clean-up environment
            rs.close();
            return listaUsuarios;
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

    public static ArrayList<Roles> getAllRols() {
        inicializarBBDD();
        try {
            // Ejecuta la consulta SQL.
            stmt = conn.createStatement();
            String sql = "SELECT * FROM roles";
            rs = stmt.executeQuery(sql);

            // Inicializar lista para guardar los roles existentes.
            ArrayList<Roles> listaRoles = new ArrayList<Roles>();
            while (rs.next()) {
                // Guarda el rol en la lista de roles.
                Roles injectRoles = (Roles) injection.getBean("roles");
                injectRoles.setNombreRol(rs.getString("roles.role_name"));
                injectRoles.setDescripcion(rs.getString("roles.role_description"));
                listaRoles.add(injectRoles);
            }
            return listaRoles;
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

    public static String anadirUsuario(String usuario, String passwd, String rol) {
        inicializarBBDD();
        try {
            if (usuario.equals("")){
                return "El campo 'usuario' está vacio";
            }else if (passwd.equals("")){
                return "El campo 'contraseña' está vacio";
            }else{
                //http://stackoverflow.com/questions/3226390/sql-insert-system-date-into-table
                // Insertar USUARIO.
                String sqlUser = "INSERT INTO users (user_name, user_pass) VALUES(?,?)";
                PreparedStatement statementUser = conn.prepareStatement(sqlUser);
                statementUser.setString(1, usuario);
                statementUser.setString(2, passwd);
                statementUser.execute();

                // Insertar ROL relacionado con usuario.
                String sqlRol = "INSERT INTO user_roles (user_name, role_name) VALUES(?,?)";
                PreparedStatement statementRol = conn.prepareStatement(sqlRol);
                statementRol.setString(1, usuario);
                statementRol.setString(2, rol);
                statementRol.execute();
                return "Insertado";
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return "Error SQL: " + sqlex.getMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error general: " + ex.getMessage();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

    public static String borrarUsuario(String usuario) {
        inicializarBBDD();
        try {
            if (usuario.equals("")){
                return "No se ha especificado al usuario";
            }else{
                // https://www.tutorialspoint.com/jdbc/jdbc-delete-records.htm
                stmt = conn.createStatement();

                // Borrar rol relacionado con usuario.
                String sqlRol = "DELETE FROM user_roles WHERE user_name = '" + usuario + "'";
                stmt.executeUpdate(sqlRol);

                // Borrar usuario.
                String sqlUser = "DELETE FROM users WHERE user_name = '" + usuario + "'";
                stmt.executeUpdate(sqlUser);

                return "Borrado";
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return "Error SQL: " + sqlex.getMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error general: " + ex.getMessage();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

    public static String modificarUsuario(String usuario, String nuevoPassword, String nuevoRol) {

        inicializarBBDD();
        try {
            if (usuario.equals("")) {
                return "Falta especificar el usuario a modificar";
            } else {
                // https://www.tutorialspoint.com/jdbc/jdbc-delete-records.htm
                stmt = conn.createStatement();

                //control de campos modificados comprobando primero la tabla de los usuarios y comprobando por separado cual de los valores se ha modficado
                if (!nuevoPassword.equals("")) {
                    // modificar y contraseña.
                    String sqlUser = "UPDATE users Set user_pass='" + nuevoPassword + "' WHERE user_name = '" + usuario + "'";
                    stmt.executeUpdate(sqlUser);
                }

                //control de campos modificados comprobando primero la tabla de los rols y comprobando por separado cual de los valores se ha modficado
                if (!nuevoRol.equals("")) {
                    // modificar rol relacionado con usuario.
                    String sqlRol = "UPDATE user_roles Set role_name='" + nuevoRol + "'  WHERE user_name = '" + usuario + "'";
                    stmt.executeUpdate(sqlRol);
                }

                return "Modificado";
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return "Error SQL: " + sqlex.getMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error general: " + ex.getMessage();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

    public static String anadirRol(String rolName, String rolDescription) {
        inicializarBBDD();
        try {
            if (rolName.equals("")){
                return "No se ha especificado el nombre de rol";
            }else{
                //http://stackoverflow.com/questions/3226390/sql-insert-system-date-into-table
                // Insertar ROL.
                String sqlRol = "INSERT INTO roles (role_name, role_description) VALUES(?,?)";
                PreparedStatement statementRol = conn.prepareStatement(sqlRol);
                statementRol.setString(1, rolName);
                statementRol.setString(2, rolDescription);
                statementRol.execute();

                return "Insertado";
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return "Error SQL: " + sqlex.getMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error general: " + ex.getMessage();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

    public static String borrarRol(String rolName) {
        inicializarBBDD();
        try {
            if (rolName.equals("")){
                return "No se ha especificado el nombre de rol";
            }else if(rolName.equals("admin")){
                return "No se puede borrar rol administrador";
            }else{
                // https://www.tutorialspoint.com/jdbc/jdbc-delete-records.htm
                stmt = conn.createStatement();

                String buscarUsuario = "SELECT * FROM user_roles WHERE role_name = (SELECT role_name FROM user_roles WHERE role_name = '" + rolName + "')";
                rs = stmt.executeQuery(buscarUsuario);
                String usuariosConRol = "";
                while (rs.next()){
                    usuariosConRol += rs.getString("user_roles.user_name") + ", ";
                }

                if (!usuariosConRol.equals("")){
                    usuariosConRol = usuariosConRol.substring(0, usuariosConRol.length() - 2);
                    return "El rol pertence al los siguientes usuarios: " + usuariosConRol;
                }

                // Borrar rol relacionado con usuario.
                String sqlRol = "DELETE FROM roles WHERE role_name = '" + rolName + "'";
                filasAfectadas = stmt.executeUpdate(sqlRol);

                if (filasAfectadas > 0){
                    return "Borrado";
                }else{
                    return "No se ha borrado ningún rol";
                }
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return "Error SQL: " + sqlex.getMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error general: " + ex.getMessage();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

    public static String modificarRol(String rol, String nuevoRolName, String nuevoRolDescription) {
        inicializarBBDD();
        try {
            if (rol.equals("")){
                return "No se ha especificado el nombre de rol";
            }else{
                // https://www.tutorialspoint.com/jdbc/jdbc-delete-records.htm
                stmt = conn.createStatement();

                //control de campos modificados comprobando primero la tabla de los rols y
                // comprobando por separado cual de los valores se quiere modificar
                if (!nuevoRolName.equals("") && !nuevoRolDescription.equals("")) {
                    String sqlRol = "UPDATE roles Set role_name='" + nuevoRolName + "', role_description='" + nuevoRolDescription + "'  WHERE role_name = '" + rol + "'";
                    stmt.executeUpdate(sqlRol);
                } else if (!nuevoRolName.equals("")) {
                    String sqlRol = "UPDATE roles Set role_name='" + nuevoRolName + "' WHERE role_name = '" + rol + "'";
                    stmt.executeUpdate(sqlRol);
                } else {
                    String sqlRol = "UPDATE roles Set role_description='" + nuevoRolDescription + "'  WHERE role_name = '" + rol + "'";
                    filasAfectadas = stmt.executeUpdate(sqlRol);
                }

                if (filasAfectadas > 0){
                    return "Modificado";
                }else{
                    return "Sin modificaciones";
                }
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return "Error SQL: " + sqlex.getMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error general: " + ex.getMessage();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

}
