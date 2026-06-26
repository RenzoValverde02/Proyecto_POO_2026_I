package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import basededatos.BaseDeDatosUsuario;
import modelo.Empleado;
import modelo.Usuario;

public class VistaEmpleados extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtUsuario;
	private JPasswordField txtContrasena;
	private JTable tblEmpleados;
	private DefaultTableModel modeloTabla;
	private JComboBox<String> cmbRol;
	
	private int idUsuarioLogueado;
	private BaseDeDatosUsuario bd = new BaseDeDatosUsuario();

	public VistaEmpleados(int idUsuarioLogueado) {
		this.idUsuarioLogueado = idUsuarioLogueado;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("GESTIÓN DE EMPLEADOS");
		lblTitulo.setForeground(new Color(0, 51, 51));
		lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
		lblTitulo.setBounds(20, 15, 300, 30);
		contentPane.add(lblTitulo);
		
		
		JLabel lblID = new JLabel("ID:");
		lblID.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblID.setBounds(20, 70, 80, 25);
		contentPane.add(lblID);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(110, 70, 180, 25);
		contentPane.add(txtID);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNombre.setBounds(20, 110, 80, 25);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(110, 110, 180, 25);
		contentPane.add(txtNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblApellido.setBounds(20, 150, 80, 25);
		contentPane.add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(110, 150, 180, 25);
		contentPane.add(txtApellido);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblUsuario.setBounds(20, 190, 80, 25);
		contentPane.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(110, 190, 180, 25);
		contentPane.add(txtUsuario);
		
		JLabel lblPass = new JLabel("Clave:");
		lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblPass.setBounds(20, 230, 80, 25);
		contentPane.add(lblPass);
		
		txtContrasena = new JPasswordField();
		txtContrasena.setBounds(110, 230, 180, 25);
		contentPane.add(txtContrasena);
		
		JLabel lblRol = new JLabel("Rol:");
		lblRol.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblRol.setBounds(20, 270, 80, 25);
		contentPane.add(lblRol);
		
		cmbRol = new JComboBox<String>();
		cmbRol.addItem("Seleccione...");
		cmbRol.addItem("Administrador");
		cmbRol.addItem("Mozo");
		cmbRol.setBounds(110, 270, 180, 25);
		contentPane.add(cmbRol);
		
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBackground(new Color(46, 204, 113));
		btnAgregar.setForeground(Color.WHITE);
		btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnAgregar.setBounds(20, 320, 130, 35);
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregar();
			}
		});
		contentPane.add(btnAgregar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(52, 152, 219));
		btnModificar.setForeground(Color.WHITE);
		btnModificar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnModificar.setBounds(160, 320, 130, 35);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificar();
			}
		});
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(new Color(231, 76, 60));
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnEliminar.setBounds(20, 365, 130, 35);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminar();
			}
		});
		contentPane.add(btnEliminar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBackground(new Color(149, 165, 166));
		btnLimpiar.setForeground(Color.WHITE);
		btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnLimpiar.setBounds(160, 365, 130, 35);
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCajas();
			}
		});
		contentPane.add(btnLimpiar);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(310, 70, 450, 330);
		contentPane.add(scrollPane);
		
		tblEmpleados = new JTable();
		tblEmpleados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				seleccionarFila();
			}
		});
		modeloTabla = new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"ID", "Nombre", "Apellido", "Usuario", "Rol"
			}
		) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };
			public boolean isCellEditable(int row, int column) { return columnEditables[column]; }
		};
		tblEmpleados.setModel(modeloTabla);
		scrollPane.setViewportView(tblEmpleados);
		
		JButton btnRegresar = new JButton("REGRESAR");
		btnRegresar.setBackground(new Color(0, 51, 51));
		btnRegresar.setForeground(new Color(255, 255, 255));
		btnRegresar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnRegresar.setBounds(610, 20, 150, 30);
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPrincipal menu = new MenuPrincipal(VistaEmpleados.this.idUsuarioLogueado);
				menu.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnRegresar);
		
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon(VistaEmpleados.class.getResource("/imagenes/fondo_restaurante_blanco.png")));
		lblFondo.setBounds(0, 0, 800, 500);
		contentPane.add(lblFondo);
		
		cargarTabla();
	}
	
	
	private void cargarTabla() {
		modeloTabla.setRowCount(0);
		try {
			ArrayList<Usuario> lista = bd.listarUsuarios();
			for (Usuario u : lista) {
				modeloTabla.addRow(new Object[] {
					u.getIdUsuario(),
					u.getNombre(),
					u.getApellido(),
					u.getUsuario(),
					u.getRol()
				});
			}
		} catch (Exception e) {
			System.out.println("Error cargando tabla: " + e);
		}
	}
	
	private void agregar() {
		if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || 
			txtUsuario.getText().isEmpty() || txtContrasena.getPassword().length == 0 || cmbRol.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "Complete todos los campos requeridos");
			return;
		}
		
		Usuario u = new Empleado();
		u.setNombre(txtNombre.getText());
		u.setApellido(txtApellido.getText());
		u.setUsuario(txtUsuario.getText());
		u.setContrasena(new String(txtContrasena.getPassword()));
		u.setRol(cmbRol.getSelectedItem().toString());
		
		if (bd.agregarUsuario(u)) {
			JOptionPane.showMessageDialog(this, "Empleado registrado exitosamente");
			limpiarCajas();
			cargarTabla();
		} else {
			JOptionPane.showMessageDialog(this, "Error al registrar empleado");
		}
	}
	
	private void modificar() {
		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla");
			return;
		}
		
		Usuario u = new Empleado();
		u.setIdUsuario(Integer.parseInt(txtID.getText()));
		u.setNombre(txtNombre.getText());
		u.setApellido(txtApellido.getText());
		u.setUsuario(txtUsuario.getText());
		u.setContrasena(new String(txtContrasena.getPassword()));
		u.setRol(cmbRol.getSelectedItem().toString());
		
		if (bd.modificarUsuario(u)) {
			JOptionPane.showMessageDialog(this, "Empleado modificado exitosamente");
			limpiarCajas();
			cargarTabla();
		} else {
			JOptionPane.showMessageDialog(this, "Error al modificar empleado");
		}
	}
	
	private void eliminar() {
		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla");
			return;
		}
		
		int id = Integer.parseInt(txtID.getText());
		int confirmacion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar al empleado?", "Confirmar", JOptionPane.YES_NO_OPTION);
		
		if (confirmacion == JOptionPane.YES_OPTION) {
			if (bd.eliminarUsuario(id)) {
				JOptionPane.showMessageDialog(this, "Empleado eliminado");
				limpiarCajas();
				cargarTabla();
			} else {
				JOptionPane.showMessageDialog(this, "Error al eliminar");
			}
		}
	}
	
	private void seleccionarFila() {
		int fila = tblEmpleados.getSelectedRow();
		if (fila >= 0) {
			txtID.setText(modeloTabla.getValueAt(fila, 0).toString());
			txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
			txtApellido.setText(modeloTabla.getValueAt(fila, 2).toString());
			txtUsuario.setText(modeloTabla.getValueAt(fila, 3).toString());
			cmbRol.setSelectedItem(modeloTabla.getValueAt(fila, 4).toString());
		}
	}
	
	private void limpiarCajas() {
		txtID.setText("");
		txtNombre.setText("");
		txtApellido.setText("");
		txtUsuario.setText("");
		txtContrasena.setText("");
		cmbRol.setSelectedIndex(0);
	}
}
