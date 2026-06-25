package vistas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import basededatos.BaseDeDatosRestaurante;
import modelo.Producto;

public class VistaCarta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtNombre;
	private JTextField txtPrecio;
	private JComboBox<String> cmbCategoria;
	private JComboBox<String> cmbEstado;
	private JTable tblCarta;
	private DefaultTableModel modeloTabla;
	
	private int idUsuarioLogueado;
	private BaseDeDatosRestaurante bd = new BaseDeDatosRestaurante();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaCarta frame = new VistaCarta(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VistaCarta(int idUsuario) {
		this.idUsuarioLogueado = idUsuario;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		setTitle("Gestión de Carta - SysGourmet");
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 242, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	
		JLabel lblTitulo = new JLabel("CARTA");
		lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
		lblTitulo.setBounds(20, 15, 86, 30);
		contentPane.add(lblTitulo);
		
		// BOTON REGRESAR
		JButton btnRegresar = new JButton("REGRESAR");
		btnRegresar.setBackground(new Color(52, 73, 94));
		btnRegresar.setForeground(Color.WHITE);
		btnRegresar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPrincipal menu = new MenuPrincipal(idUsuarioLogueado);
				menu.setVisible(true);
				dispose();
			}
		});
		btnRegresar.setBounds(580, 15, 130, 30);
		contentPane.add(btnRegresar);
		
		// FORMULARIO 
		JLabel lblId = new JLabel("ID (Automático):");
		lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblId.setBounds(20, 70, 120, 20);
		contentPane.add(lblId);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(140, 70, 150, 25);
		contentPane.add(txtId);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNombre.setBounds(20, 110, 120, 20);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(140, 110, 150, 25);
		contentPane.add(txtNombre);
		
		JLabel lblPrecio = new JLabel("Precio (S/):");
		lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblPrecio.setBounds(20, 150, 120, 20);
		contentPane.add(lblPrecio);
		
		txtPrecio = new JTextField();
		txtPrecio.setBounds(140, 150, 150, 25);
		contentPane.add(txtPrecio);
		
		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblCategoria.setBounds(20, 190, 120, 20);
		contentPane.add(lblCategoria);
		
		cmbCategoria = new JComboBox<String>();
		cmbCategoria.addItem("ENTRADA");
		cmbCategoria.addItem("FONDO");
		cmbCategoria.addItem("POSTRE");
		cmbCategoria.addItem("BEBIDA");
		cmbCategoria.setBounds(140, 190, 150, 25);
		contentPane.add(cmbCategoria);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblEstado.setBounds(20, 230, 120, 20);
		contentPane.add(lblEstado);
		
		cmbEstado = new JComboBox<String>();
		cmbEstado.addItem("DISPONIBLE");
		cmbEstado.addItem("NO DISPONIBLE");
		cmbEstado.setBounds(140, 230, 150, 25);
		contentPane.add(cmbEstado);
		
		// BOTONES DE ACCION
		JButton btnGuardar = new JButton("Agregar");
		btnGuardar.setBackground(new Color(46, 204, 113));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarProducto();
			}
		});
		btnGuardar.setBounds(20, 280, 135, 35);
		contentPane.add(btnGuardar);
		
		JButton btnActualizar = new JButton("Modificar");
		btnActualizar.setBackground(new Color(52, 152, 219));
		btnActualizar.setForeground(Color.WHITE);
		btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarProducto();
			}
		});
		btnActualizar.setBounds(165, 280, 135, 35);
		contentPane.add(btnActualizar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(new Color(231, 76, 60));
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarProducto();
			}
		});
		btnEliminar.setBounds(20, 330, 135, 35);
		contentPane.add(btnEliminar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBackground(new Color(149, 165, 166));
		btnLimpiar.setForeground(Color.WHITE);
		btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCajas();
			}
		});
		btnLimpiar.setBounds(165, 330, 135, 35);
		contentPane.add(btnLimpiar);
		
		// TABLA 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(320, 70, 390, 295);
		contentPane.add(scrollPane);
		
		tblCarta = new JTable();
		modeloTabla = new DefaultTableModel(
			new Object[][] {},
			new String[] { "ID", "Nombre", "Precio", "Categoría" }
		);
		tblCarta.setModel(modeloTabla);
		tblCarta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				seleccionarFila();
			}
		});
		scrollPane.setViewportView(tblCarta);
		
		// CARGAR DATOS INICIALES
		cargarTabla();
		
		// FONDO DE PANTALLA
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new javax.swing.ImageIcon(VistaCarta.class.getResource("/imagenes/fondo_restaurante_blanco.png")));
		lblFondo.setBounds(0, 0, 750, 461);
		contentPane.add(lblFondo);
	}
	

	private void cargarTabla() {
		modeloTabla.setRowCount(0);
		try {
			ArrayList<Producto> lista = bd.listarProductos(); 
			for (Producto p : lista) {
				String categoria = p.getClass().getSimpleName().toUpperCase(); 
				modeloTabla.addRow(new Object[] {
					p.getIdItem(),
					p.getNombre(),
					p.getPrecio(),
					categoria
				});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error cargando la carta.");
		}
	}
	
	private void seleccionarFila() {
		int fila = tblCarta.getSelectedRow();
		if (fila >= 0) {
			txtId.setText(tblCarta.getValueAt(fila, 0).toString());
			txtNombre.setText(tblCarta.getValueAt(fila, 1).toString());
			txtPrecio.setText(tblCarta.getValueAt(fila, 2).toString());
			
		}
	}
	
	private void agregarProducto() {
		String nombre = txtNombre.getText().trim();
		String precioStr = txtPrecio.getText().trim();
		String categoria = cmbCategoria.getSelectedItem().toString();
		String estado = cmbEstado.getSelectedItem().toString();
		
		if (nombre.isEmpty() || precioStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Completa nombre y precio.");
			return;
		}
		
		try {
			double precio = Double.parseDouble(precioStr);
			if (bd.insertarProducto(nombre, precio, categoria, estado)) {
				JOptionPane.showMessageDialog(this, "Producto agregado.");
				limpiarCajas();
				cargarTabla();
			} else {
				JOptionPane.showMessageDialog(this, "Error al guardar en BD.");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El precio debe ser un número.");
		}
	}
	
	private void actualizarProducto() {
		if (txtId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.");
			return;
		}
		
		int id = Integer.parseInt(txtId.getText());
		String nombre = txtNombre.getText().trim();
		String precioStr = txtPrecio.getText().trim();
		String categoria = cmbCategoria.getSelectedItem().toString();
		String estado = cmbEstado.getSelectedItem().toString();
		
		try {
			double precio = Double.parseDouble(precioStr);
			if (bd.actualizarProducto(id, nombre, precio, categoria, estado)) {
				JOptionPane.showMessageDialog(this, "Producto actualizado.");
				limpiarCajas();
				cargarTabla();
			} else {
				JOptionPane.showMessageDialog(this, "Error al actualizar en BD.");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El precio debe ser un número.");
		}
	}
	
	private void eliminarProducto() {
		if (txtId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.");
			return;
		}
		
		int id = Integer.parseInt(txtId.getText());
	
		if (bd.cambiarEstadoProducto(id, "NO DISPONIBLE")) {
			JOptionPane.showMessageDialog(this, "Producto ocultado del menú exitosamente.");
			limpiarCajas();
			cargarTabla();
		} else {
			JOptionPane.showMessageDialog(this, "Error al eliminar en BD.");
		}
	}
	
	private void limpiarCajas() {
		txtId.setText("");
		txtNombre.setText("");
		txtPrecio.setText("");
		cmbCategoria.setSelectedIndex(0);
		cmbEstado.setSelectedIndex(0);
	}
}
