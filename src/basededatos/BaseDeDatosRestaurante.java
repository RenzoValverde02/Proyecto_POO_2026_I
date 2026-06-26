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

    // ==========================================
    // METODOS CRUD PARA GESTION DE MENU (CARTA)
    // ==========================================
    
    public boolean insertarProducto(String nombre, double precio, String categoria, String estado) {
        String sql = "INSERT INTO menu_items (nombre, precio, categoria, estado) VALUES (?, ?, ?, ?)";
        try {
            Connection cn = Conexion_mysql.conectar();
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setDouble(2, precio);
            pst.setString(3, categoria);
            pst.setString(4, estado);
            pst.executeUpdate();
            cn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error insertando producto: " + e.getMessage());
            return false;
        }
    }
    
    public boolean actualizarProducto(int idItem, String nombre, double precio, String categoria, String estado) {
        String sql = "UPDATE menu_items SET nombre=?, precio=?, categoria=?, estado=? WHERE id_item=?";
        try {
            Connection cn = Conexion_mysql.conectar();
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setDouble(2, precio);
            pst.setString(3, categoria);
            pst.setString(4, estado);
            pst.setInt(5, idItem);
            pst.executeUpdate();
            cn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error actualizando producto: " + e.getMessage());
            return false;
        }
    }
    
    public boolean cambiarEstadoProducto(int idItem, String nuevoEstado) {
        String sql = "UPDATE menu_items SET estado=? WHERE id_item=?";
        try {
            Connection cn = Conexion_mysql.conectar();
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, nuevoEstado);
            pst.setInt(2, idItem);
            pst.executeUpdate();
            cn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error cambiando estado de producto: " + e.getMessage());
            return false;
        }
    }
	// === METODOS PARA MODULO CAJA ===
	
	public java.util.ArrayList<String> listarPedidosPendientes() {
		java.util.ArrayList<String> lista = new java.util.ArrayList<>();
		String sql = "SELECT p.id_pedido, m.numero_mesa FROM pedidos p INNER JOIN mesas m ON p.id_mesa = m.id_mesa WHERE p.estado = 'PENDIENTE'";
		try {
			Connection cn = Conexion_mysql.conectar();
			PreparedStatement pst = cn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				lista.add(rs.getInt("id_pedido") + " - Mesa " + rs.getString("numero_mesa"));
			}
			cn.close();
		} catch (SQLException e) {
			System.out.println("Error listando pedidos pendientes: " + e.getMessage());
		}
		return lista;
	}
	
	public javax.swing.table.DefaultTableModel obtenerDetallePedidoParaCaja(int idPedido) {
		javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(
			new Object[][] {},
			new String[] { "Producto", "Cant.", "Precio Unit.", "Subtotal" }
		) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		
		String sql = "SELECT m.nombre, d.cantidad, d.precio_unitario, (d.cantidad * d.precio_unitario) as subtotal " +
					 "FROM detalle_pedido d INNER JOIN menu_items m ON d.id_item = m.id_item " +
					 "WHERE d.id_pedido = ?";
		try {
			Connection cn = Conexion_mysql.conectar();
			PreparedStatement pst = cn.prepareStatement(sql);
			pst.setInt(1, idPedido);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				modelo.addRow(new Object[] {
					rs.getString("nombre"),
					rs.getInt("cantidad"),
					rs.getDouble("precio_unitario"),
					rs.getDouble("subtotal")
				});
			}
			cn.close();
		} catch (SQLException e) {
			System.out.println("Error obteniendo detalle de caja: " + e.getMessage());
		}
		return modelo;
	}
	
	public boolean cobrarPedido(int idPedido) {
		// 1. Marcar pedido como PAGADO
		// 2. Liberar la mesa
		String sqlPedido = "UPDATE pedidos SET estado = 'PAGADO' WHERE id_pedido = ?";
		String sqlMesa = "UPDATE mesas SET estado = 1 WHERE id_mesa = (SELECT id_mesa FROM pedidos WHERE id_pedido = ?)";
		
		try {
			Connection cn = Conexion_mysql.conectar();
			cn.setAutoCommit(false); // Iniciar transaccion
			
			// Actualizar Mesa
			PreparedStatement pstMesa = cn.prepareStatement(sqlMesa);
			pstMesa.setInt(1, idPedido);
			pstMesa.executeUpdate();
			
			// Actualizar Pedido
			PreparedStatement pstPedido = cn.prepareStatement(sqlPedido);
			pstPedido.setInt(1, idPedido);
			pstPedido.executeUpdate();
			
			cn.commit();
			cn.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Error cobrando pedido: " + e.getMessage());
			return false;
		}
	}
}
