package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

//IMPORTS AGREGADODOS POR RENZO
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import conexion.Conexion_mysql;

public class FrmMesasPedidos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCantidad;
	private JTable tblPedido;
	
	//VARIABLES DECLARADAS POR RENZO
	
	DefaultTableModel modelo;
	double total = 0.00;
	String[] columnas = {"Comida", "Cantidad", "Precio", "Sub total"};
	
	private JComboBox<String> cmbMesa;
	private JComboBox<String> cmbProducto;
	private JLabel lblTotal;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMesasPedidos frame = new FrmMesasPedidos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmMesasPedidos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 691, 446);
		contentPane = new JPanel();
		contentPane.setBackground(new java.awt.Color(240, 242, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MESAS Y PEDIDOS");
		lblNewLabel.setFont(new java.awt.Font("Segoe UI Black", java.awt.Font.BOLD, 22));
		lblNewLabel.setForeground(new java.awt.Color(41, 128, 185));
		lblNewLabel.setBounds(20, 11, 250, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mesa :");
		lblNewLabel_1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		lblNewLabel_1.setForeground(java.awt.Color.BLACK);
		lblNewLabel_1.setBounds(20, 60, 60, 25);
		contentPane.add(lblNewLabel_1);
		
		//cambiamos el nombre x variable cbmmesa y cbm producto
		cmbMesa = new JComboBox<>();
		cmbMesa.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
		cmbMesa.setBounds(100, 60, 250, 30);
		contentPane.add(cmbMesa);
		
		JLabel lblNewLabel_2 = new JLabel("Producto :");
		lblNewLabel_2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		lblNewLabel_2.setForeground(java.awt.Color.BLACK);
		lblNewLabel_2.setBounds(20, 105, 80, 25);
		contentPane.add(lblNewLabel_2);
		
		cmbProducto = new JComboBox<>();
		cmbProducto.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
		cmbProducto.setBounds(100, 105, 250, 30);
		contentPane.add(cmbProducto);
		
		JLabel lblNewLabel_3 = new JLabel("Cantidad :");
		lblNewLabel_3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		lblNewLabel_3.setForeground(java.awt.Color.BLACK);
		lblNewLabel_3.setBounds(20, 150, 80, 25);
		contentPane.add(lblNewLabel_3);
		
		txtCantidad = new JTextField();
		txtCantidad.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
		txtCantidad.setBounds(100, 150, 80, 30);
		contentPane.add(txtCantidad);
		txtCantidad.setColumns(10);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		btnAgregar.setBackground(new java.awt.Color(41, 128, 185));
		btnAgregar.setForeground(java.awt.Color.WHITE);
		btnAgregar.setFocusPainted(false);
		btnAgregar.setBorderPainted(false);
		
		//agregamos el metodo actionlistener 
		btnAgregar.addActionListener(e->agregarProducto());
		
		btnAgregar.setBounds(413, 60, 160, 40);
		contentPane.add(btnAgregar);
		
		JButton btnGuardarPedido = new JButton("Guardar Pedido");
		btnGuardarPedido.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		btnGuardarPedido.setBackground(new java.awt.Color(46, 204, 113));
		btnGuardarPedido.setForeground(java.awt.Color.WHITE);
		btnGuardarPedido.setFocusPainted(false);
		btnGuardarPedido.setBorderPainted(false);
		btnGuardarPedido.addActionListener(e->guardarPedido());
		btnGuardarPedido.setBounds(413, 115, 160, 40);
		contentPane.add(btnGuardarPedido);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 200, 553, 120);
		contentPane.add(scrollPane);
		
		tblPedido = new JTable();
		tblPedido.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Comida", "Cantidad", "Precio", "Sub total"
			}
		));
		scrollPane.setViewportView(tblPedido);
		
		JLabel lblNewLabel_5 = new JLabel("Total :");
		lblNewLabel_5.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
		lblNewLabel_5.setForeground(java.awt.Color.BLACK);
		lblNewLabel_5.setBounds(390, 335, 60, 25);
		contentPane.add(lblNewLabel_5);
		
		
		//canbiamos la variable
		lblTotal = new JLabel("S/ 0.00");
		lblTotal.setFont(new java.awt.Font("Segoe UI Black", java.awt.Font.BOLD, 22));
		lblTotal.setForeground(new java.awt.Color(192, 57, 43));
		lblTotal.setBounds(460, 335, 120, 30);
		contentPane.add(lblTotal);
		
		//objeto y seteamos la tablaPedido x renzo  
		modelo = new DefaultTableModel(null,columnas);
		tblPedido.setModel(modelo);
		
		//METODOS PARA LLAMAR PRODUCTOS Y MESES  DE BD 
		cargarProductos();
		cargarMesas();
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new javax.swing.ImageIcon(FrmMesasPedidos.class.getResource("/imagenes/fondo_restaurante_blanco.png")));
		lblFondo.setBounds(0, 0, 691, 446);
		contentPane.add(lblFondo);

	}
	
	// metodo agregar producto x renzo

	private void agregarProducto() {
		
		//validacion 
		
		if(cmbMesa.getSelectedIndex() == 0) {

		    JOptionPane.showMessageDialog(null,
		            "Selecciona una mesa.");

		    return;
		}

		if(cmbProducto.getSelectedIndex() == 0) {

		    JOptionPane.showMessageDialog(null,
		            "Selecciona una comida o bebida.");

		    return;
		}

		String producto = cmbProducto.getSelectedItem().toString();
		
		String[] datos = producto.split(" - S/ ");
		String nombreProducto = datos[0];
		double precio = Double.parseDouble(datos[1]);
		
		//validar que no este vacio la cantidad
		if (txtCantidad.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Ingrese la cantidad.");
			return;
		}
		
		//validar que no sea letra el txtcantidad
		String textoCantidad = txtCantidad.getText().trim();

		if (!textoCantidad.matches("\\d+")) {

		    JOptionPane.showMessageDialog(null,
		            "La cantidad debe ser un número.");

		    return;
		}
		
		
		int cantidad = Integer.parseInt(textoCantidad);

		double subtotal = precio * cantidad;

		modelo.addRow(new Object[] { 
				nombreProducto, 
				cantidad,  
				String.format("S/ %.2f", precio),
				String.format("S/ %.2f", subtotal) });

		total += subtotal;

		lblTotal.setText(String.format("S/ %.2f", total));

		txtCantidad.setText("");

		
	}
	
	//METODO PARA CARGAR PRODUCTOS DE LA BD X RENZO
	
	private void cargarProductos() {

		try {

			Connection cn = Conexion_mysql.conectar();

			String sql = "SELECT id_item, nombre, precio FROM menu_items WHERE estado = 'DISPONIBLE'";

			PreparedStatement ps = cn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			cmbProducto.removeAllItems();
			cmbProducto.addItem("...");

			while (rs.next()) {

				cmbProducto.addItem(
					    //rs.getInt("id_item") + " - " +
					    rs.getString("nombre") + " - S/ " +
					    rs.getDouble("precio")
					);
			}

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Error al cargar productos");

		}

	}
	
	//METODO PARA CARGAR MESAS X RENZO
	
	private void cargarMesas() {

	    try {

	        Connection cn = Conexion_mysql.conectar();

	        String sql = "SELECT id_mesa, numero_mesa, capacidad FROM mesas WHERE estado = 'LIBRE'";

	        PreparedStatement ps = cn.prepareStatement(sql);

	        ResultSet rs = ps.executeQuery();

	        cmbMesa.removeAllItems();
	        cmbMesa.addItem("...");

	        while (rs.next()) {

	        	cmbMesa.addItem(
	        		  //rs.getInt("id_mesa") + " - " +
	        		    rs.getString("numero_mesa") + " - " +
	        		    rs.getInt("capacidad") + " personas"
	        		);

	        }

	    } catch (Exception e) {

	        JOptionPane.showMessageDialog(null,
	                "Error al cargar mesas");

	    }

	}
	
	
	//obtener el numero de mesa con el id en la bd
	
	private int obtenerIdMesa(String textoMesa) {

	    int idMesa = 0;

	    try {

	        String numeroMesa = textoMesa.split(" - ")[0];

	        Connection cn = Conexion_mysql.conectar();

	        String sql = "SELECT id_mesa FROM mesas WHERE numero_mesa = ?";

	        PreparedStatement ps = cn.prepareStatement(sql);

	        ps.setString(1, numeroMesa);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            idMesa = rs.getInt("id_mesa");
	        }

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al obtener mesa");
	    }

	    return idMesa;
	}
	
	
	//obtiene el  id del producto  de la bd
	
	private int obtenerIdProducto(String textoProducto) {

	    int idProducto = 0;

	    try {

	        String nombreProducto = textoProducto.split(" - S/ ")[0];

	        Connection cn = Conexion_mysql.conectar();

	        String sql = "SELECT id_item FROM menu_items WHERE nombre = ?";

	        PreparedStatement ps = cn.prepareStatement(sql);

	        ps.setString(1, nombreProducto);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            idProducto = rs.getInt("id_item");

	        }

	    } catch (Exception e) {

	        JOptionPane.showMessageDialog(null,
	                "Error al obtener producto");

	    }

	    return idProducto;
	}
	
	
	private void guardarPedido() {

	    try {
	        if (modelo.getRowCount() == 0) {
	            JOptionPane.showMessageDialog(null, "Agrega productos al pedido.");
	            return;
	        }

	        String textoMesa = cmbMesa.getSelectedItem().toString();
	        int idMesa = obtenerIdMesa(textoMesa);
	        int idMozo = 1; // temporal para prueba

	        Connection cn = Conexion_mysql.conectar();

	        String sqlPedido = "INSERT INTO pedidos(id_mesa, id_mozo, estado) VALUES (?, ?, 'PENDIENTE')";
	        PreparedStatement psPedido = cn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);

	        psPedido.setInt(1, idMesa);
	        psPedido.setInt(2, idMozo);
	        psPedido.executeUpdate();

	        ResultSet rs = psPedido.getGeneratedKeys();
	        int idPedido = 0;

	        if (rs.next()) {
	            idPedido = rs.getInt(1);
	        }

	        for (int i = 0; i < modelo.getRowCount(); i++) {

	            String nombreProducto = modelo.getValueAt(i, 0).toString();
	            int idProducto = obtenerIdProducto(nombreProducto + " - S/ 0");
	            int cantidad = Integer.parseInt(modelo.getValueAt(i, 1).toString());
	            double precio = Double.parseDouble(
	            modelo.getValueAt(i, 2).toString().replace("S/", "").trim()
	            );

	            String sqlDetalle = "INSERT INTO detalle_pedido(id_pedido, id_item, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
	            PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle);

	            psDetalle.setInt(1, idPedido);
	            psDetalle.setInt(2, idProducto);
	            psDetalle.setInt(3, cantidad);
	            psDetalle.setDouble(4, precio);
	           

	            psDetalle.executeUpdate();
	        }

	        String sqlMesa = "UPDATE mesas SET estado='OCUPADA' WHERE id_mesa=?";
	        PreparedStatement psMesa = cn.prepareStatement(sqlMesa);
	        psMesa.setInt(1, idMesa);
	        psMesa.executeUpdate();

	        JOptionPane.showMessageDialog(null, "Pedido guardado correctamente.");

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al guardar pedido: " + e.getMessage());
	    }
	}
	
	
	
	
}

