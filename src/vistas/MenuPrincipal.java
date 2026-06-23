package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import vistas.FrmMesasPedidos;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
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
	public MenuPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 401);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 242, 245)); // Gris clarito elegante
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(255, 255, 255));
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(new Color(128, 0, 0)); // Azul como el del Login
		panel.setBounds(0, 0, 506, 42);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("SysGourmet ");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		panel.add(lblNewLabel);
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Showcard Gothic", Font.ITALIC, 24));
		
		JButton btnNewButton = new JButton("<html><center>Mesas y<br>Pedidos</center></html>");
		btnNewButton.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/icono_mesas.png")));
		btnNewButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnNewButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBackground(new Color(0, 139, 139));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//llevar al otro jframe mesas 
				 FrmMesasPedidos frm = new FrmMesasPedidos();

			        frm.setVisible(true);

			        dispose();
			}
		});
		btnNewButton.setBounds(69, 53, 133, 142);
		contentPane.add(btnNewButton);
		
		JButton btncarta = new JButton("Carta");
		btncarta.setVerticalTextPosition(SwingConstants.BOTTOM);
		btncarta.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/icono_carta.png")));
		btncarta.setHorizontalTextPosition(SwingConstants.CENTER);
		btncarta.setForeground(Color.WHITE);
		btncarta.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
		btncarta.setBorderPainted(false);
		btncarta.setFocusPainted(false);
		btncarta.setBackground(new Color(50, 205, 50));
		btncarta.setBounds(312, 53, 133, 142);
		contentPane.add(btncarta);
		
		JButton btnempleados = new JButton("Empleados");
		btnempleados.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnempleados.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/icono_empleados.png")));
		btnempleados.setHorizontalTextPosition(SwingConstants.CENTER);
		btnempleados.setForeground(Color.WHITE);
		btnempleados.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
		btnempleados.setBorderPainted(false);
		btnempleados.setFocusPainted(false);
		btnempleados.setBackground(new Color(255, 165, 0));
		btnempleados.setBounds(69, 206, 133, 142);
		contentPane.add(btnempleados);
		
		JButton btncobrosYCaja = new JButton("<html><center>Cobros<br>y Caja</center></html>");
		btncobrosYCaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btncobrosYCaja.setVerticalTextPosition(SwingConstants.BOTTOM);
		btncobrosYCaja.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/icono_caja.png")));
		btncobrosYCaja.setHorizontalTextPosition(SwingConstants.CENTER);
		btncobrosYCaja.setForeground(Color.WHITE);
		btncobrosYCaja.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
		btncobrosYCaja.setBorderPainted(false);
		btncobrosYCaja.setFocusPainted(false);
		btncobrosYCaja.setBackground(new Color(128, 0, 128));
		btncobrosYCaja.setBounds(312, 206, 133, 142);
		contentPane.add(btncobrosYCaja);
		
		// Fondo Oscuro de Restaurante
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/fondo_oscuro.png")));
		lblFondo.setBounds(0, 42, 506, 320); // El tamaño exacto sin tapar la barra azul
		contentPane.add(lblFondo);

	}
}
