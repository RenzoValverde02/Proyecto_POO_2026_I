package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;

import basededatos.BaseDeDatosRestaurante;

public class VistaCaja extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblDetalle;
	private DefaultTableModel modeloTabla;
	private JComboBox<String> cmbPedidos;
	private JLabel lblTotal;
	
	private int idUsuarioLogueado;
	private BaseDeDatosRestaurante bd = new BaseDeDatosRestaurante();

	public VistaCaja(int idUsuarioLogueado) {
		this.idUsuarioLogueado = idUsuarioLogueado;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255)); 
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("COBROS Y CAJA");
		lblTitulo.setForeground(new Color(0, 51, 51));
		lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
		lblTitulo.setBounds(20, 15, 200, 30);
		contentPane.add(lblTitulo);
		
	
		JLabel lblSeleccione = new JLabel("Seleccione Pedido Pendiente:");
		lblSeleccione.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblSeleccione.setBounds(20, 70, 250, 25);
		contentPane.add(lblSeleccione);
		
		cmbPedidos = new JComboBox<String>();
		cmbPedidos.setBounds(220, 70, 200, 25);
		cmbPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarDetalle();
			}
		});
		contentPane.add(cmbPedidos);
		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 110, 545, 230);
		contentPane.add(scrollPane);
		
		tblDetalle = new JTable();
		modeloTabla = new DefaultTableModel(
			new Object[][] {},
			new String[] { "Producto", "Cant.", "Precio Unit.", "Subtotal" }
		) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		tblDetalle.setModel(modeloTabla);
		scrollPane.setViewportView(tblDetalle);
		
		
		JLabel lblTotalTexto = new JLabel("TOTAL A COBRAR:");
		lblTotalTexto.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
		lblTotalTexto.setBounds(20, 360, 200, 30);
		contentPane.add(lblTotalTexto);
		
		lblTotal = new JLabel("S/ 0.00");
		lblTotal.setForeground(new Color(220, 20, 60)); // Crimson
		lblTotal.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
		lblTotal.setBounds(210, 360, 150, 30);
		contentPane.add(lblTotal);
		
		JButton btnCobrar = new JButton("Cobrar y Liberar Mesa");
		btnCobrar.setBackground(new Color(46, 204, 113));
		btnCobrar.setForeground(Color.WHITE);
		btnCobrar.setFont(new Font("Segoe UI Black", Font.BOLD, 14));
		btnCobrar.setBounds(365, 355, 200, 40);
		btnCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procesarCobro();
			}
		});
		contentPane.add(btnCobrar);
		
		
		JButton btnRegresar = new JButton("REGRESAR");
		btnRegresar.setForeground(new Color(255, 255, 255));
		btnRegresar.setBackground(new Color(0, 51, 51));
		btnRegresar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnRegresar.setBounds(415, 20, 150, 30);
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPrincipal menu = new MenuPrincipal(VistaCaja.this.idUsuarioLogueado);
				menu.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnRegresar);
		
	
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon(VistaCaja.class.getResource("/imagenes/fondo_restaurante_blanco.png")));
		lblFondo.setBounds(0, 0, 600, 500);
		contentPane.add(lblFondo);
		
		
		cargarPedidosPendientes();
	}
	

	
	private void cargarPedidosPendientes() {
		cmbPedidos.removeAllItems();
		cmbPedidos.addItem("Seleccione...");
		ArrayList<String> pedidos = bd.listarPedidosPendientes();
		for (String p : pedidos) {
			cmbPedidos.addItem(p);
		}
	}
	
	private void cargarDetalle() {
		if (cmbPedidos.getSelectedIndex() <= 0) {
			modeloTabla.setRowCount(0);
			lblTotal.setText("S/ 0.00");
			return;
		}
		

		String seleccion = cmbPedidos.getSelectedItem().toString();
		int idPedido = Integer.parseInt(seleccion.split(" ")[0]);
		
	
		modeloTabla = bd.obtenerDetallePedidoParaCaja(idPedido);
		tblDetalle.setModel(modeloTabla);
		
		
		double total = 0;
		for (int i = 0; i < modeloTabla.getRowCount(); i++) {
			total += Double.parseDouble(modeloTabla.getValueAt(i, 3).toString());
		}
		
		lblTotal.setText(String.format("S/ %.2f", total));
	}
	
	private void procesarCobro() {
		if (cmbPedidos.getSelectedIndex() <= 0) {
			JOptionPane.showMessageDialog(this, "Seleccione un pedido para cobrar.");
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog(this, "¿Confirmar el cobro de " + lblTotal.getText() + " y liberar la mesa?", "Confirmar Cobro", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			String seleccion = cmbPedidos.getSelectedItem().toString();
			int idPedido = Integer.parseInt(seleccion.split(" ")[0]);
			
			if (bd.cobrarPedido(idPedido)) {
				
				
				
				
				//  BOLETA TXT 
				try {
					String nombreArchivo = "Boleta_Pedido_" + idPedido + ".txt";
					FileWriter archivo = new FileWriter(nombreArchivo);
					PrintWriter escritor = new PrintWriter(archivo);
					
					escritor.println("=====================================");
					escritor.println("        RESTAURANTE SYSGOURMET       ");
					escritor.println("        Comprobante de Pago          ");
					escritor.println("=====================================");
					escritor.println("Pedido Nro: " + idPedido);
					escritor.println(seleccion.substring(seleccion.indexOf("-") + 2)); 
					escritor.println("-------------------------------------");
					escritor.println("CANT | PRODUCTO        | SUBTOTAL");
					escritor.println("-------------------------------------");
					
					for (int i = 0; i < modeloTabla.getRowCount(); i++) {
						String prod = modeloTabla.getValueAt(i, 0).toString();
						String cant = modeloTabla.getValueAt(i, 1).toString();
						String sub = modeloTabla.getValueAt(i, 3).toString();
						escritor.println(cant + " x  " + prod + "  =  S/ " + sub);
					}
					
					escritor.println("-------------------------------------");
					escritor.println("TOTAL PAGADO: " + lblTotal.getText());
					escritor.println("=====================================");
					escritor.println("      ¡Gracias por su visita!        ");
					escritor.close();
					new ProcessBuilder("notepad", nombreArchivo).start();
					
				} catch (Exception ex) {
					System.out.println("Error generando boleta: " + ex.getMessage());
				}
				// ---------------------------------
				
				JOptionPane.showMessageDialog(this, "¡Cobro exitoso! La mesa ha sido liberada.");
				cargarPedidosPendientes(); 
				modeloTabla.setRowCount(0); 
				lblTotal.setText("S/ 0.00");
			} else {
				JOptionPane.showMessageDialog(this, "Hubo un error al procesar el cobro.");
			}
		}
	}
}
