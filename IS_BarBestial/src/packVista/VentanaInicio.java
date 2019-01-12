package packVista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaInicio extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textUsuario;

    private JButton btnJugar;
    private JButton btnAyuda;
    private JButton btnRanking;

    private JButton btnSeleccionarConfig;
    private JButton btnCrearConfig;

    private JButton btnContinuarPartida;
    private JButton btnRegistrarse;
    private JButton btnRecuperarContrasea;
    private JTextField textContrasena;
    private JLabel lblContrasea;
    private JPanel panel;

    /**
     * Create the frame.
     */
    public VentanaInicio() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 850, 620);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        /* Centrar */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize(); //Tamaño del frame actual (ancho x alto)
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        
        panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        
                this.btnRanking = new JButton("Ranking");
                panel.add(btnRanking);
                btnRanking.setFont(new Font("Tahoma", Font.PLAIN, 11));
                
                this.btnSeleccionarConfig = new JButton("Sel.Config.");
                panel.add(btnSeleccionarConfig);
                btnSeleccionarConfig.setFont(new Font("Tahoma", Font.PLAIN, 11));
                
                this.btnCrearConfig = new JButton("Crear Config.");
                panel.add(btnCrearConfig);
                btnCrearConfig.setFont(new Font("Tahoma", Font.PLAIN, 11));
                
                        this.btnContinuarPartida = new JButton("Continuar Partida");
                        panel.add(btnContinuarPartida);

        JPanel panelMenu = new JPanel();
        contentPane.add(panelMenu, BorderLayout.SOUTH);

        JLabel lblIntroduceTuNombre = new JLabel("Usuario:");
        lblIntroduceTuNombre.setFont(new Font("Tahoma", Font.PLAIN, 11));
        panelMenu.add(lblIntroduceTuNombre);

        this.textUsuario = new JTextField();
        textUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
        panelMenu.add(textUsuario);
        textUsuario.setColumns(10);
        
        lblContrasea = new JLabel("Contrase\u00F1a:");
        lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 11));
        panelMenu.add(lblContrasea);
        
        textContrasena = new JTextField();
        textContrasena.setFont(new Font("Tahoma", Font.PLAIN, 11));
        panelMenu.add(textContrasena);
        textContrasena.setColumns(10);

        this.btnJugar = new JButton("Iniciar Sesi\u00F3n");
        btnJugar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        panelMenu.add(btnJugar);
        
        btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setFont(new Font("Tahoma", Font.PLAIN, 11));
        panelMenu.add(btnRegistrarse);
        
        btnRecuperarContrasea = new JButton("Recuperar Contrase\u00F1a");
        btnRecuperarContrasea.setFont(new Font("Tahoma", Font.PLAIN, 11));
        panelMenu.add(btnRecuperarContrasea);

        this.btnAyuda = new JButton("Ayuda");
        btnAyuda.setFont(new Font("Tahoma", Font.PLAIN, 11));
        panelMenu.add(btnAyuda);
        
        JPanel panelImagenBar = new JPanel();
        contentPane.add(panelImagenBar, BorderLayout.CENTER);

        JLabel labelBar = new JLabel("");
        labelBar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        labelBar.setIcon(new ImageIcon(VentanaInicio.class.getResource("/images/Bar.png")));
        panelImagenBar.add(labelBar);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                VentanaInicio frame = new VentanaInicio();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    
    public void addIniciarSesionListener(ActionListener listenForBtnJugar) {
        btnJugar.addActionListener(listenForBtnJugar);
    }

    public void addAyudaListener(ActionListener listenForBtnAyuda) {
        btnAyuda.addActionListener(listenForBtnAyuda);
    }

    public void addRankingListener(ActionListener listenForBtnRanking) {
        btnRanking.addActionListener(listenForBtnRanking);
    }
    
    public void addCrearConfigListener(ActionListener listenForBtnCrearConfig) {
        btnCrearConfig.addActionListener(listenForBtnCrearConfig);
    }
    
    public void addSeleccionarConfigListener(ActionListener listenForBtnSelConfig) {
        btnSeleccionarConfig.addActionListener(listenForBtnSelConfig);
    }

    public void addContinuarListener(ActionListener listenForBtnContinuar) {
    	btnContinuarPartida.addActionListener(listenForBtnContinuar);
    }
    
    public void addRegistrarseListener(ActionListener listenForBtnRegistrarse) {
    	btnRegistrarse.addActionListener(listenForBtnRegistrarse);
    }
    
    public void addRecuperarContrasenaListener(ActionListener listenForBtnRecuperarContrasena) {
    	btnRecuperarContrasea.addActionListener(listenForBtnRecuperarContrasena);
    }
    

    public void showNombreErrorMessage() {
        JOptionPane.showMessageDialog(this,
                "Introduce un nombre.");
    }

    public void cerrarVentana() {
        setVisible(false);
        dispose();
    }

	public String getTextUsuario() {
		return textUsuario.toString();
	}

	public void setTextUsuario(JTextField textUsuario) {
		this.textUsuario = textUsuario;
	}

	public String getTextContrasena() {
		return textContrasena.toString();
	}

	public void setTextContrasena(JTextField textContrasena) {
		this.textContrasena = textContrasena;
	}

    
}