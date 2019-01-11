package packVista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class VentanaCambiarContraseña extends JFrame {

	private JPanel contentPane;
	private JTextField textNContraseña;
	private JButton btnCambiar;
	private String MUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCambiarContraseña frame = new VentanaCambiarContraseña("Prueba");
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
	public VentanaCambiarContraseña(String user) {
		MUser = user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 453, 193);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textNContraseña = new JTextField();
		textNContraseña.setBounds(176, 65, 86, 20);
		contentPane.add(textNContraseña);
		textNContraseña.setColumns(10);
		
		btnCambiar = new JButton("Cambiar");
		btnCambiar.setBounds(277, 64, 89, 23);
		contentPane.add(btnCambiar);
		
		JLabel lblNuevaContrasea = new JLabel("Nueva Contrase\u00F1a:");
		lblNuevaContrasea.setBounds(69, 68, 125, 14);
		contentPane.add(lblNuevaContrasea);
	}
	
	public String getUser() {
		return MUser;
	}

	public String getTextNContraseña() {
		return textNContraseña.toString();
	}

	public void addCambiarListener(ActionListener listenForBtnCambiar) {
        btnCambiar.addActionListener(listenForBtnCambiar);
    }
}
