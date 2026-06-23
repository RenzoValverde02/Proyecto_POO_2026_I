package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

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
	
	private JComboBox cmbMesa;
	private JComboBox cmbProducto;
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MESAS Y PEDIDOS");
		lblNewLabel.setBounds(110, 11, 172, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mesa :");
		lblNewLabel_1.setBounds(10, 48, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		//cambiamos el nombre x variable cbmmesa y cbm producto
		cmbMesa = new JComboBox();
		cmbMesa.setBounds(86, 44, 196, 22);
		contentPane.add(cmbMesa);
		
		JLabel lblNewLabel_2 = new JLabel("Producto :");
		lblNewLabel_2.setBounds(10, 90, 58, 14);
		contentPane.add(lblNewLabel_2);
		
		cmbProducto = new JComboBox();
		cmbProducto.setBounds(86, 87, 196, 22);
		contentPane.add(cmbProducto);
		
		JLabel lblNewLabel_3 = new JLabel("Cantidad :");
		lblNewLabel_3.setBounds(10, 132, 58, 14);
		contentPane.add(lblNewLabel_3);
		
		txtCantidad = new JTextField();
		txtCantidad.setBounds(86, 130, 46, 20);
		contentPane.add(txtCantidad);
		txtCantidad.setColumns(10);
		
		JButton btnAgregar = new JButton("Agregar");
		
		//agregamos el metodo actionlistener 
		btnAgregar.addActionListener(e->agregarProducto());
		
		btnAgregar.setBounds(413, 31, 137, 35);
		contentPane.add(btnAgregar);
		
		JButton btnGuardarPedido = new JButton("Guardar Pedido");
		btnGuardarPedido.addActionListener(e->guardarPedido());
		btnGuardarPedido.setBounds(413, 86, 137, 37);
		contentPane.add(btnGuardarPedido);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(86, 206, 530, 99);
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
		lblNewLabel_5.setBounds(86, 333, 46, 14);
		contentPane.add(lblNewLabel_5);
		
		
		//canbiamos la variable
		lblTotal = new JLabel("S/ 0.00");
		lblTotal.setBounds(133, 333, 46, 14);
		contentPane.add(lblTotal);
		
		//objeto y seteamos la tablaPedido x renzo  
		modelo = new DefaultTableModel(null,columnas);
		tblPedido.setModel(modelo);
		
		//METODOS PARA LLAMAR PRODUCTOS Y MESES  DE BD 
		cargarProductos();
		cargarMesas();
		

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
	            double precio = Double.parseDouble(modelo.getValueAt(i, 2).toString());

	            String sqlDetalle = "INSERT INTO detalle_pedido(id_pedido, id_item, cantidad, precio_unitario, nota) VALUES (?, ?, ?, ?, ?)";
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
