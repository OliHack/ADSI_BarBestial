package packVista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import packModelo.GestorRanking;

public class Ranking extends JFrame {
	//Submenu principal para redirigir a las distintas interfaces segun el tipo de ranking que se quiera consultar
	private JPanel contentPane;
	private static Ranking miRanking;
	private GestorRanking gestorRanking = GestorRanking.getRankingDB();

	/**
	 * Launch the application.
	 */
	public void empezar() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ranking frame = getRanking();
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
	public Ranking() {
		setTitle("Consultar Rankings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnPersonalAbsoluto = new JButton("Mis mejores puntuaciones");//Personal Absoluto
		btnPersonalAbsoluto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				miRanking.setVisible(false);
				VentanaMejIndv.getMejIndv().actualizarRanking(gestorRanking.getMejInd());
				VentanaMejIndv.getMejIndv().setVisible(true);
			}
		});
		btnPersonalAbsoluto.setBounds(123, 30, 209, 37);
		contentPane.add(btnPersonalAbsoluto);
		
		JButton btnPersonalPorNiveles = new JButton("Mejor de hoy");//Mejor de hoy
		btnPersonalPorNiveles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				miRanking.setVisible(false);
				VentanaHoy.getHoy().actualizarRanking(gestorRanking.getMejHoy());
				VentanaHoy.getHoy().setVisible(true);
			}
		});
		btnPersonalPorNiveles.setBounds(123, 78, 209, 37);
		contentPane.add(btnPersonalPorNiveles);
		
		JButton btnNewButton = new JButton("Mejores de todos los tiempos");//Mejor de todos siempre
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				miRanking.setVisible(false);
				VentanaSiempre.getSiempre().actualizarRanking(gestorRanking.getMejSiempre());
				VentanaSiempre.getSiempre().setVisible(true);
			}
		});
		btnNewButton.setBounds(123, 126, 209, 37);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Jugadores con mejor media");//Mejores medias
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				miRanking.setVisible(false);
				VentanaMedias.getMedias().actualizarRanking(gestorRanking.getMejMedias());
				VentanaMedias.getMedias().setVisible(true);
			}
		});
		btnNewButton_1.setBounds(123, 174, 209, 37);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Volver");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				miRanking.setVisible(false);
			}
		});
		btnNewButton_2.setBounds(318, 228, 89, 23);
		contentPane.add(btnNewButton_2);
	}
	
	public static Ranking getRanking() {
		if (miRanking == null) {
			miRanking = new Ranking();
		}
		return miRanking;
	}
}
