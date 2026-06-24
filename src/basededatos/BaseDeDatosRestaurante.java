package basededatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.Conexion_mysql;
import modelo.Bebida;
import modelo.Mesa;
import modelo.Plato;
import modelo.Producto;

public class BaseDeDatosRestaurante {

    // LISTAR PRODUCTOS 
    public ArrayList<Producto> listarProductos() throws SQLException {
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT id_item, nombre, precio, categoria FROM menu_items WHERE estado = 'DISPONIBLE'";
        
        Connection cn = Conexion_mysql.conectar();
        if(cn == null) throw new SQLException("No hay conexion");
        
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            int id = rs.getInt("id_item");
            String nombre = rs.getString("nombre");
            double precio = rs.getDouble("precio");
            String categoria = rs.getString("categoria");
            
            Producto p;
            if ("BEBIDA".equals(categoria)) {
                p = new Bebida(id, nombre, precio);
            } else {
                p = new Plato(id, nombre, precio); 
            }
            lista.add(p);
        }
        cn.close();
        return lista;
    }

    // LISTAR PRODUCTOS POR CATEGORIA
    public ArrayList<Producto> listarProductosPorCategoria(String categoria) throws SQLException {
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT id_item, nombre, precio FROM menu_items WHERE estado = 'DISPONIBLE' AND categoria = ?";
        
        Connection cn = Conexion_mysql.conectar();
        if(cn == null) throw new SQLException("No hay conexion");

        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setString(1, categoria);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            int id = rs.getInt("id_item");
            String nombre = rs.getString("nombre");
            double precio = rs.getDouble("precio");
            
            Producto p;
            if (categoria.equals("BEBIDA")) {
                p = new Bebida(id, nombre, precio);
            } else {
                p = new Plato(id, nombre, precio); 
            }
            lista.add(p);
        }
        cn.close();
        return lista;
    }

    // BUSCAR PRODUCTOS POR NOMBRE
    public ArrayList<Producto> listarProductos(String palabraClave) throws SQLException {
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT id_item, nombre, precio, categoria FROM menu_items WHERE estado = 'DISPONIBLE' AND nombre LIKE ?";
        
        Connection cn = Conexion_mysql.conectar();
        if(cn == null) throw new SQLException("No hay conexion");

        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setString(1, "%" + palabraClave + "%");
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            int id = rs.getInt("id_item");
            String nombre = rs.getString("nombre");
            double precio = rs.getDouble("precio");
            String categoria = rs.getString("categoria");
            
            Producto p;
            if ("BEBIDA".equals(categoria)) {
                p = new Bebida(id, nombre, precio);
            } else {
                p = new Plato(id, nombre, precio);
            }
            lista.add(p);
        }
        cn.close();
        return lista;
    }

    // LISTAR MESAS
    public ArrayList<Mesa> listarMesas() throws SQLException {
        ArrayList<Mesa> lista = new ArrayList<>();
        String sql = "SELECT id_mesa, numero_mesa, capacidad FROM mesas WHERE estado = 'LIBRE'";
        
        Connection cn = Conexion_mysql.conectar();
        if(cn == null) throw new SQLException("No hay conexion");

        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            Mesa m = new Mesa(
                rs.getInt("id_mesa"),
                rs.getString("numero_mesa"),
                rs.getInt("capacidad"),
                "LIBRE"
            );
            lista.add(m);
        }
        cn.close();
        return lista;
    }
}
