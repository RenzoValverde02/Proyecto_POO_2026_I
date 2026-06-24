package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class LOGIN extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private JTextField txtUsuario;
	private JPasswordField txtContrasena;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LOGIN frame = new LOGIN();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public LOGIN() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		panel.setBackground(new Color(41, 128, 185));
		panel.setBounds(0, 0, 434, 42);
		contentPane.add(panel);
		
		JLabel lblTitulo = new JLabel("SysGourmet");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 24));
		lblTitulo.setForeground(new Color(255, 255, 255));
		panel.add(lblTitulo);
		
		JLabel lblContraseña = new JLabel("Contraseña");
		lblContraseña.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblContraseña.setForeground(UIManager.getColor("Button.darkShadow"));
		lblContraseña.setBounds(100, 156, 250, 20);
		contentPane.add(lblContraseña);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblUsuario.setForeground(UIManager.getColor("Button.darkShadow"));
		lblUsuario.setBounds(100, 81, 250, 20);
		contentPane.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setForeground(UIManager.getColor("CheckBox.darkShadow"));
		txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtUsuario.setBounds(100, 106, 250, 35);
		txtUsuario.setBorder(javax.swing.BorderFactory.createCompoundBorder(
				javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200)), 
				javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		txtContrasena = new JPasswordField();
		txtContrasena.setForeground(UIManager.getColor("CheckBox.darkShadow"));
		txtContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtContrasena.setBounds(100, 180, 250, 35);
		txtContrasena.setBorder(javax.swing.BorderFactory.createCompoundBorder(
				javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200)), 
				javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		contentPane.add(txtContrasena);
		
		JButton btnIngresar = new JButton("INGRESAR");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String usuario = txtUsuario.getText();
				String contrasena = new String(txtContrasena.getPassword());
				
				if (usuario.equals("") || contrasena.equals("")) {
				    
				    javax.swing.JOptionPane.showMessageDialog(null, "Faltan llenar datos");
				} else {
				    
				   
				    basededatos.BaseDeDatosUsuario baseDeDatos = new basededatos.BaseDeDatosUsuario();
				    modelo.Usuario u = baseDeDatos.login(usuario, contrasena);
				    
				  
				    if (u != null) {
					    javax.swing.JOptionPane.showMessageDialog(null, "Bienvenido " + u.getNombre());
					    
					  
					    MenuPrincipal menu = new MenuPrincipal(u.getIdUsuario());
					    menu.setVisible(true);
					    
					    
					    LOGIN.this.dispose();
					    
					} else {
					    javax.swing.JOptionPane.showMessageDialog(null, "Error: Datos incorrectos");
				    }
				}
			}
				
			
		});
		btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnIngresar.setBackground(new Color(41, 128, 185)); 
		btnIngresar.setForeground(Color.WHITE); 
		btnIngresar.setFocusPainted(false);
		btnIngresar.setBorderPainted(false); 
		btnIngresar.setBounds(100, 240, 250, 45); 
		contentPane.add(btnIngresar);

	}
}
