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
	// === METODOS CRUD PARA EMPLEADOS ===
	
	public java.util.ArrayList<modelo.Usuario> listarUsuarios() {
		java.util.ArrayList<modelo.Usuario> lista = new java.util.ArrayList<>();
		String sql = "SELECT * FROM usuarios WHERE estado = 1";
		try {
			Connection cn = Conexion_mysql.conectar();
			PreparedStatement pst = cn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Usuario usu = new modelo.Empleado();
				usu.setIdUsuario(rs.getInt("id_usuario"));
				usu.setNombre(rs.getString("nombre"));
				usu.setApellido(rs.getString("apellido"));
				usu.setUsuario(rs.getString("usuario"));
				usu.setContrasena(rs.getString("contrasena"));
				usu.setRol(rs.getString("rol"));
				usu.setEstado(rs.getInt("estado"));
				lista.add(usu);
			}
			cn.close();
		} catch (Exception e) {
			System.out.println("Error al listar usuarios: " + e);
		}
		return lista;
	}
	
	public boolean agregarUsuario(modelo.Usuario usu) {
		String sql = "INSERT INTO usuarios (nombre, apellido, usuario, contrasena, rol, estado) VALUES (?, ?, ?, ?, ?, 1)";
		try {
			Connection cn = Conexion_mysql.conectar();
			PreparedStatement pst = cn.prepareStatement(sql);
			pst.setString(1, usu.getNombre());
			pst.setString(2, usu.getApellido());
			pst.setString(3, usu.getUsuario());
			pst.setString(4, usu.getContrasena());
			pst.setString(5, usu.getRol());
			pst.execute();
			cn.close();
			return true;
		} catch (Exception e) {
			System.out.println("Error al agregar usuario: " + e);
			return false;
		}
	}
	
	public boolean modificarUsuario(modelo.Usuario usu) {
		String sql = "UPDATE usuarios SET nombre=?, apellido=?, usuario=?, contrasena=?, rol=? WHERE id_usuario=?";
		try {
			Connection cn = Conexion_mysql.conectar();
			PreparedStatement pst = cn.prepareStatement(sql);
			pst.setString(1, usu.getNombre());
			pst.setString(2, usu.getApellido());
			pst.setString(3, usu.getUsuario());
			pst.setString(4, usu.getContrasena());
			pst.setString(5, usu.getRol());
			pst.setInt(6, usu.getIdUsuario());
			pst.execute();
			cn.close();
			return true;
		} catch (Exception e) {
			System.out.println("Error al modificar usuario: " + e);
			return false;
		}
	}
	
	public boolean eliminarUsuario(int idUsuario) {
		// Borrado logico
		String sql = "UPDATE usuarios SET estado=0 WHERE id_usuario=?";
		try {
			Connection cn = Conexion_mysql.conectar();
			PreparedStatement pst = cn.prepareStatement(sql);
			pst.setInt(1, idUsuario);
			pst.execute();
			cn.close();
			return true;
		} catch (Exception e) {
			System.out.println("Error al eliminar usuario: " + e);
			return false;
		}
	}
	
	public String obtenerRol(int idUsuario) {
		String rol = "";
		String sql = "SELECT rol FROM usuarios WHERE id_usuario = ?";
		try {
			Connection cn = Conexion_mysql.conectar();
			PreparedStatement pst = cn.prepareStatement(sql);
			pst.setInt(1, idUsuario);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				rol = rs.getString("rol");
			}
			cn.close();
		} catch (Exception e) {
			System.out.println("Error obteniendo rol: " + e);
		}
		return rol;
	}
}
