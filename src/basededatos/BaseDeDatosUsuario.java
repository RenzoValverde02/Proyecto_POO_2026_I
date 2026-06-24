package basededatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import conexion.Conexion_mysql;
import modelo.Usuario;

public class BaseDeDatosUsuario {
	
// VALIDACION DE INICIO DE SESION

    public Usuario login(String usuario, String contrasena) {
        Usuario usu = null;
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ? AND estado = 1";
        
        try {
            Connection cn = Conexion_mysql.conectar();
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, usuario);
            pst.setString(2, contrasena);
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                usu = new modelo.Empleado();
                usu.setIdUsuario(rs.getInt("id_usuario"));
                usu.setNombre(rs.getString("nombre"));
                usu.setApellido(rs.getString("apellido"));
                usu.setUsuario(rs.getString("usuario"));
                usu.setContrasena(rs.getString("contrasena"));
                usu.setRol(rs.getString("rol"));
                usu.setEstado(rs.getInt("estado"));
            }
            
            cn.close();
        } catch (Exception e) {
            System.out.println("Error en Login BaseDeDatos: " + e.getMessage());
        }
        
        return usu;
    }
}
