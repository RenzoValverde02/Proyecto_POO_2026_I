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


import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import conexion.Conexion_mysql;

public class VistaPedidos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCantidad;
	private JTextField txtNotas;
	private JTable tblPedido;
	
	//VARIABLES 
	
	DefaultTableModel modelo;
	double total = 0.00;
	String[] columnas = {"Comida", "Cantidad", "Notas", "Precio", "Sub total", "Código"};
	
	private JComboBox<Object> cmbMesa;
	private JComboBox<modelo.Producto> cmbProducto;
	private JComboBox<String> cmbCategoria;
	private JLabel lblTotal;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaPedidos frame = new VistaPedidos(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private int idMozoActual;

	
	public VistaPedidos(int idMozoLogueado) {
		this.idMozoActual = idMozoLogueado;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 691, 520);
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
		
		JButton btnRegresar = new JButton("Volver al Menú");
		btnRegresar.setBackground(new java.awt.Color(52, 73, 94));
		btnRegresar.setForeground(java.awt.Color.WHITE);
		btnRegresar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
		btnRegresar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				MenuPrincipal menu = new MenuPrincipal(idMozoActual);
				menu.setVisible(true);
				dispose();
			}
		});
		btnRegresar.setBounds(520, 15, 130, 30);
		contentPane.add(btnRegresar);
		
		JLabel lblNewLabel_1 = new JLabel("Mesa :");
		lblNewLabel_1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		lblNewLabel_1.setForeground(java.awt.Color.BLACK);
		lblNewLabel_1.setBounds(20, 60, 60, 25);
		contentPane.add(lblNewLabel_1);
		
		
		cmbMesa = new JComboBox<>();
		cmbMesa.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
		cmbMesa.setBounds(100, 60, 250, 30);
		contentPane.add(cmbMesa);
		
		JLabel lblCategoria = new JLabel("Categoría :");
		lblCategoria.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		lblCategoria.setForeground(java.awt.Color.BLACK);
		lblCategoria.setBounds(20, 100, 80, 25);
		contentPane.add(lblCategoria);
		
		cmbCategoria = new JComboBox<>(new String[] {"TODAS", "ENTRADA", "FONDO", "POSTRE", "BEBIDA"});
		cmbCategoria.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
		cmbCategoria.setBounds(100, 100, 250, 30);
		contentPane.add(cmbCategoria);
		
		cmbCategoria.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				cargarProductosPorCategoria();
			}
		});

		JLabel lblNewLabel_2 = new JLabel("Producto :");
		lblNewLabel_2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		lblNewLabel_2.setForeground(java.awt.Color.BLACK);
		lblNewLabel_2.setBounds(20, 140, 80, 25);
		contentPane.add(lblNewLabel_2);
		
		cmbProducto = new JComboBox<>();
		cmbProducto.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
		cmbProducto.setBounds(100, 140, 250, 30);
		contentPane.add(cmbProducto);
		
		JLabel lblNotas = new JLabel("Notas :");
		lblNotas.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		lblNotas.setForeground(java.awt.Color.BLACK);
		lblNotas.setBounds(20, 180, 80, 25);
		contentPane.add(lblNotas);
		
		txtNotas = new JTextField();
		txtNotas.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
		txtNotas.setBounds(100, 180, 250, 20);
		contentPane.add(txtNotas);
		txtNotas.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Cantidad :");
		lblNewLabel_3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		lblNewLabel_3.setForeground(java.awt.Color.BLACK);
		lblNewLabel_3.setBounds(20, 215, 80, 25);
		contentPane.add(lblNewLabel_3);
		
		txtCantidad = new JTextField();
		txtCantidad.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
		txtCantidad.setBounds(100, 215, 80, 30);
		contentPane.add(txtCantidad);
		txtCantidad.setColumns(10);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		btnAgregar.setBackground(new java.awt.Color(41, 128, 185));
		btnAgregar.setForeground(java.awt.Color.WHITE);
		btnAgregar.setFocusPainted(false);
		btnAgregar.setBorderPainted(false);
		
		
		btnAgregar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				agregarProducto();
			}
		});
		
		btnAgregar.setBounds(413, 60, 160, 40);
		contentPane.add(btnAgregar);
		
		JButton btnGuardarPedido = new JButton("Guardar Pedido");
		btnGuardarPedido.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		btnGuardarPedido.setBackground(new java.awt.Color(46, 204, 113));
		btnGuardarPedido.setForeground(java.awt.Color.WHITE);
		btnGuardarPedido.setFocusPainted(false);
		btnGuardarPedido.setBorderPainted(false);
		btnGuardarPedido.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				guardarPedido();
			}
		});
		btnGuardarPedido.setBounds(413, 115, 160, 40);
		contentPane.add(btnGuardarPedido);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 255, 553, 120);
		contentPane.add(scrollPane);
		
		tblPedido = new JTable();
		tblPedido.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Comida", "Cantidad", "Notas", "Precio", "Sub total", "Código"
			}
		));
		scrollPane.setViewportView(tblPedido);
		
		JLabel lblNewLabel_5 = new JLabel("Total :");
		lblNewLabel_5.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
		lblNewLabel_5.setForeground(java.awt.Color.BLACK);
		lblNewLabel_5.setBounds(390, 395, 60, 25);
		contentPane.add(lblNewLabel_5);
		
		
		
		lblTotal = new JLabel("S/ 0.00");
		lblTotal.setFont(new java.awt.Font("Segoe UI Black", java.awt.Font.BOLD, 22));
		lblTotal.setForeground(new java.awt.Color(192, 57, 43));
		lblTotal.setBounds(460, 395, 120, 30);
		contentPane.add(lblTotal);
		
	
		modelo = new DefaultTableModel(null,columnas);
		tblPedido.setModel(modelo);
		
//METODOS PARA LLAMAR PRODUCTOS Y MESAS  DE BD 
		cargarProductos();
		cargarMesas();
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new javax.swing.ImageIcon(VistaPedidos.class.getResource("/imagenes/fondo_restaurante_blanco.png")));
		lblFondo.setBounds(0, 0, 691, 520);
		contentPane.add(lblFondo);

	}

// CONFIGURACION DE LA INTERFAZ Y TABLA

	// AGREGAR PRODUCTO A LA TABLA
	private void agregarProducto() {
		if(cmbMesa.getSelectedIndex() <= 0) {
		    JOptionPane.showMessageDialog(null, "Selecciona una mesa.");
		    return;
		}
		if(cmbProducto.getSelectedIndex() <= 0) {
		    JOptionPane.showMessageDialog(null, "Selecciona una comida o bebida.");
		    return;
		}

		modelo.Producto producto = (modelo.Producto) cmbProducto.getSelectedItem();
		double precio = producto.getPrecio();
		
		int cantidad = 0;
		
		try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) {
            	JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a cero");
    		    return;
            }
		} catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: Ingrese un numero valido");
		    return;
		}

		String notas = txtNotas.getText().trim();
		double subtotal = precio * cantidad;

		
		modelo.addRow(new Object[] { 
				producto.obtenerDetalle(),
				cantidad,
				notas,
				"S/ " + precio,
				"S/ " + subtotal,
                producto.getIdItem()
        });

		total += subtotal;
		lblTotal.setText("S/ " + total);
		txtCantidad.setText("");
		txtNotas.setText("");
	}
	
//METODO PARA CARGAR PRODUCTOS DE LA BD 
	
	// CARGAR PRODUCTOS DE LA BD
	private void cargarProductos() {
	    try {
	        basededatos.BaseDeDatosRestaurante bd = new basededatos.BaseDeDatosRestaurante();
	        java.util.ArrayList<modelo.Producto> productos = bd.listarProductos();
	        cmbProducto.removeAllItems();
	        
	        cmbProducto.addItem(new modelo.Plato(0, "...", 0.0) {
	            @Override
	            public String toString() {
	                return "...";
	            }
	        }); 
	        
	        for (modelo.Producto p : productos) {
	            cmbProducto.addItem(p);
	        }
	    } catch (Exception e) {
	        javax.swing.JOptionPane.showMessageDialog(this, "Error al cargar productos");
	    }
	}
	
	// CARGAR PRODUCTOS POR CATEGORIA
	private void cargarProductosPorCategoria() {
	    try {
	        String catSeleccionada = (String) cmbCategoria.getSelectedItem();
	        basededatos.BaseDeDatosRestaurante bd = new basededatos.BaseDeDatosRestaurante();
	        java.util.ArrayList<modelo.Producto> productos;
	        
	        if (catSeleccionada.equals("TODAS")) {
	            productos = bd.listarProductos();
	        } else {
	            productos = bd.listarProductosPorCategoria(catSeleccionada);
	        }
	        
	        cmbProducto.removeAllItems();
	        
	        cmbProducto.addItem(new modelo.Plato(0, "...", 0.0) {
	            @Override
	            public String toString() {
	                return "...";
	            }
	        });
	        
	        for (modelo.Producto p : productos) {
	            cmbProducto.addItem(p);
	        }
	    } catch (Exception e) {
	        javax.swing.JOptionPane.showMessageDialog(this, "Error al cargar productos filtrados");
	    }
	}
	
// CARGAR MESAS DE LA BD
	private void cargarMesas() {
	    try {
            basededatos.BaseDeDatosRestaurante db = new basededatos.BaseDeDatosRestaurante();
            java.util.ArrayList<modelo.Mesa> mesas = db.listarMesas();

	        cmbMesa.removeAllItems();
	        cmbMesa.addItem("...");

            for (modelo.Mesa m : mesas) {
                cmbMesa.addItem(m);
            }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al cargar mesas");
	    }
	}
	
	
	// GUARDAR PEDIDO EN LA BASE DE DATOS
	private void guardarPedido() {
	    try {
	        if (modelo.getRowCount() == 0) {
	            JOptionPane.showMessageDialog(null, "Agrega productos al pedido.");
	            return;
	        }

	        modelo.Mesa mesaSeleccionada = (modelo.Mesa) cmbMesa.getSelectedItem();
	        int idMesa = mesaSeleccionada.getIdMesa();
	      
	        int idMozo = this.idMozoActual; 

	        Connection cn = Conexion_mysql.conectar();
            if(cn == null) throw new SQLException("Error de conexión a la BD");

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
	        
	            int idProducto = Integer.parseInt(modelo.getValueAt(i, 5).toString()); 
	          
	            int cantidad = Integer.parseInt(modelo.getValueAt(i, 1).toString());
	     
	            String notas = modelo.getValueAt(i, 2).toString();
	       
	            double precio = Double.parseDouble(
	                modelo.getValueAt(i, 3).toString().replace("S/", "").trim()
	            );

	            
	            String sqlDetalle = "INSERT INTO detalle_pedido(id_pedido, id_item, cantidad, precio_unitario, nota) VALUES (?, ?, ?, ?, ?)";
	            PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle);

	            psDetalle.setInt(1, idPedido);
	            psDetalle.setInt(2, idProducto);
	            psDetalle.setInt(3, cantidad);
	            psDetalle.setDouble(4, precio);
	            psDetalle.setString(5, notas);

	            psDetalle.executeUpdate();
	        }

	        String sqlMesa = "UPDATE mesas SET estado='OCUPADA' WHERE id_mesa=?";
	        PreparedStatement psMesa = cn.prepareStatement(sqlMesa);
	        psMesa.setInt(1, idMesa);
	        psMesa.executeUpdate();
            cn.close();

	        JOptionPane.showMessageDialog(null, "Pedido guardado correctamente.");

         
            modelo.setRowCount(0);
            total = 0.0;
            lblTotal.setText("S/ 0.00");
            cmbMesa.setSelectedIndex(0);
            cmbProducto.setSelectedIndex(0);

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al guardar pedido");
	    }
	}
	
	
	
	
}

