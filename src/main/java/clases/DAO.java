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
    static ApplicationContext injection = new ClassPathXmlApplicationContext("/conf/spring.conf.xml");
    private static Statement stmt;
    private static Connection conn;
    private static ResultSet rs;

    public static void inicializarBBDD(){
        // Nombdre de Driver JDBC y la dirección URL para conectarse.
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        //final String DB_URL = "jdbc:mysql://172.16.12.151/dwes"; // Instituto
        final String DB_URL = "jdbc:mysql://172.16.12.151/authorization"; // toni change bbdd insti
        //final String DB_URL = "jdbc:mysql://192.168.1.132/dwes"; // Casa

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
            se.printStackTrace();
        } catch (Exception e) {
            // Error general.
            e.printStackTrace();
        } finally {

        } //end try
    }

    public static ArrayList<Usuarios> getAllUsers(){
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
        }catch (SQLException sqlex){
            sqlex.printStackTrace();
            return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }finally {
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

    public static ArrayList<Roles> getAllRols(){
        inicializarBBDD();
        try {
            // Ejecuta la consulta SQL.
            stmt = conn.createStatement();
            String sql = "SELECT * FROM users, user_roles, roles WHERE users.user_name = user_roles.user_name " +
                    "AND  user_roles.role_name = roles.role_name";
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
        }catch (SQLException sqlex){
            sqlex.printStackTrace();
            return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }finally {
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

    public static String anadirUsuario(String nombre, String passwd, String rol){
        inicializarBBDD();
        try {
            //http://stackoverflow.com/questions/3226390/sql-insert-system-date-into-table
            // Insertar USUARIO.
            String sqlUser = "INSERT INTO users (user_name, user_pass) VALUES(?,?)";
            PreparedStatement statementUser = conn.prepareStatement(sqlUser);
            statementUser.setString(1, nombre);
            statementUser.setString(2, passwd);
            statementUser.execute();

            // Insertar ROL relacionado con usuario.
            String sqlRol = "INSERT INTO user_roles (user_name, role_name) VALUES(?,?)";
            PreparedStatement statementRol = conn.prepareStatement(sqlRol);
            statementRol.setString(1, nombre);
            statementRol.setString(2, rol);
            statementRol.execute();
            return "Insertado";
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return "Error";
        }catch (Exception ex){
            ex.printStackTrace();
            return "Error";
        }finally {
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

    public static String borrarUsuario(String usuario){
        inicializarBBDD();
        try {
            // https://www.tutorialspoint.com/jdbc/jdbc-delete-records.htm
            stmt = conn.createStatement();

            // Borrar rol relacionado con usuario.
            String sqlRol = "DELETE FROM user_roles WHERE user_name = '" + usuario + "'";
            stmt.executeUpdate(sqlRol);

            // Borrar usuario.
            String sqlUser = "DELETE FROM users WHERE user_name = '" + usuario + "'";
            stmt.executeUpdate(sqlUser);

            return "Borrado";
        }catch (SQLException sqlex){
            sqlex.printStackTrace();
            return "Error";
        }catch (Exception ex){
            ex.printStackTrace();
            return "Error";
        }finally {
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

    public static String modificarUsuario(String usuario, String nuevoUsuario, String nuevoPassword, String nuevoRol){

        inicializarBBDD();
        try {
            // https://www.tutorialspoint.com/jdbc/jdbc-delete-records.htm
            stmt = conn.createStatement();

            //control de campos modificados comprobando primero ls tabla de los usuarios y comprobando por separado cual de los valores se ha modficado
            if (nuevoUsuario != "" && nuevoPassword != "") {
                // modificar usuario y contrasena.
                String sqlUser = "UPDATE users Set user_pass='"+nuevoPassword+"', user_name='"+nuevoUsuario+"' WHERE user_name = '" + usuario + "'";
                stmt.executeUpdate(sqlUser);
            } else if (nuevoPassword != ""){
                // modificar usuario y contrasena.
                String sqlUser = "UPDATE users Set user_pass='"+nuevoPassword+"' WHERE user_name = '" + usuario + "'";
                stmt.executeUpdate(sqlUser);
            } else {
                // modificar usuario y contrasena.
                String sqlUser = "UPDATE users Set user_name='"+nuevoUsuario+"' WHERE user_name = '" + usuario + "'";
                stmt.executeUpdate(sqlUser);
            }

            //control de campos modificados comprobando primero ls tabla de los rols y comprobando por separado cual de los valores se ha modficado
            if (nuevoRol != "" && nuevoUsuario != ""){
                // modificar rol relacionado con usuario.
                String sqlRol = "UPDATE user_roles Set user_name='"+nuevoUsuario+"', role_name='"+nuevoRol+"'  WHERE user_name = '" + usuario + "'";
                stmt.executeUpdate(sqlRol);
            } else if (nuevoRol != ""){
                // modificar rol relacionado con usuario.
                String sqlRol = "UPDATE user_roles Set role_name='"+nuevoRol+"'  WHERE user_name = '" + usuario + "'";
                stmt.executeUpdate(sqlRol);
            } else {
                // modificar rol relacionado con usuario.
                String sqlRol = "UPDATE user_roles Set user_name='"+nuevoUsuario+"' WHERE user_name = '" + usuario + "'";
                stmt.executeUpdate(sqlRol);
            }


            return "Modificado";
        }catch (SQLException sqlex){
            sqlex.printStackTrace();
            return "Error";
        }catch (Exception ex){
            ex.printStackTrace();
            return "Error";
        }finally {
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

    public static String anadirRol(String rolName, String rolDescription){
        inicializarBBDD();
        try {
            //http://stackoverflow.com/questions/3226390/sql-insert-system-date-into-table
            // Insertar ROL.
            String sqlRol = "INSERT INTO roles (role_name, role_description) VALUES(?,?)";
            PreparedStatement statementRol = conn.prepareStatement(sqlRol);
            statementRol.setString(1, rolName);
            statementRol.setString(2, rolDescription);
            statementRol.execute();

            return "Insertado";
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return "Error";
        }catch (Exception ex){
            ex.printStackTrace();
            return "Error";
        }finally {
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

    public static String borrarRol(String rolName){
        inicializarBBDD();
        try {
            // https://www.tutorialspoint.com/jdbc/jdbc-delete-records.htm
            stmt = conn.createStatement();

            // Borrar rol relacionado con usuario.
            String sqlRol = "DELETE FROM roles WHERE role_name = '" + rolName + "'";
            stmt.executeUpdate(sqlRol);

            return "Borrado";
        }catch (SQLException sqlex){
            sqlex.printStackTrace();
            return "Error";
        }catch (Exception ex){
            ex.printStackTrace();
            return "Error";
        }finally {
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

    public static String modificarRol(String rol, String nuevoRolName, String nuevoRolDescription){
        inicializarBBDD();
        try {
            // https://www.tutorialspoint.com/jdbc/jdbc-delete-records.htm
            stmt = conn.createStatement();

            //control de campos modificados comprobando primero ls tabla de los rols y comprobando por separado cual de los valores se ha modficado
            if (nuevoRolName != "" && nuevoRolDescription != ""){
                // modificar rol.
                String sqlRol = "UPDATE roles Set role_name='"+nuevoRolName+"', role_description='"+nuevoRolDescription+"'  WHERE role_name = '" + rol + "'";
                stmt.executeUpdate(sqlRol);
            } else if (nuevoRolName != ""){
                // modificar rol relacionado con usuario.
                String sqlRol =  "UPDATE roles Set role_name='"+nuevoRolName+"' WHERE role_name = '" + rol + "'";
                stmt.executeUpdate(sqlRol);
            } else {
                // modificar rol relacionado con usuario.
                String sqlRol =  "UPDATE roles Set role_description='"+nuevoRolDescription+"'  WHERE role_name = '" + rol + "'";
                stmt.executeUpdate(sqlRol);
            }

            return "Modificado";
        }catch (SQLException sqlex){
            sqlex.printStackTrace();
            return "Error";
        }catch (Exception ex){
            ex.printStackTrace();
            return "Error";
        }finally {
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
