package packControlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import packModelo.*;

import packVista.*;


public class Controlador {
	private static Controlador miControlador;
	private String usuarioAct;
	private int configAct;
	
	/* Modelo */
	private Partida partida;
	private Tablero tablero;
	private GestorRanking gestorRanking;
	private Jugador jug;
	private int usos;

	private GestorConfiguraciones miGestorConfig;
	private GestorUsuarios miGestorUsuarios;

	private GestorBD misGestorBD;

	private GestorPartida miGestorPartida;


	/* Vista */
	private VentanaInicio ventanaInicio;
	private VentanaJuego ventanaJuego;
	private VentanaAyuda ventanaAyuda;

	private Ranking ventanaRanking;

	//private VentanaRanking ventanaRanking;
	private VentanaConfiguracion ventanaConfiguracion;

	private VentanaSeleccionConfig ventanaSeleccionConfig;
	private VentanaPartidasGuardadas ventanaPartidasGuardadas;
	private VentanaGuardada ventanaGuardada;
	private VentanaCambiarContrasena ventanaCambiarContrasena;
	private VentanaMejIndv ventanaMejIndv;
	private VentanaHoy ventanaHoy;
	private VentanaSiempre ventanaSiempre;
	private VentanaMedias ventanaMedias;
	
	public Controlador() {
		this.usuarioAct = "Carlos";
		this.partida = Partida.getMiPartida();
		this.tablero = Tablero.getMiTablero();
		this.gestorRanking = GestorRanking.getRankingDB();
		this.configAct = 0;
		this.usos = 0;

		this.miGestorConfig = GestorConfiguraciones.getGestorConfig();
		this.miGestorUsuarios = GestorUsuarios.getGestorUsuarios();

		this.misGestorBD = GestorBD.getGestorBD();

		this.miGestorPartida = GestorPartida.getGestorPartida();

		this.ventanaInicio = new VentanaInicio();
		this.ventanaAyuda = new VentanaAyuda();

		this.ventanaRanking = Ranking.getRanking();
		this.ventanaMejIndv = VentanaMejIndv.getMejIndv();
		this.ventanaHoy = VentanaHoy.getHoy();
		this.ventanaSiempre = VentanaSiempre.getSiempre();
		this.ventanaMedias = VentanaMedias.getMedias();
		this.ventanaJuego = new VentanaJuego();//test

		//this.ventanaRanking = new VentanaRanking();
		this.ventanaConfiguracion = new VentanaConfiguracion();

		this.ventanaSeleccionConfig = new VentanaSeleccionConfig();
		this.ventanaCambiarContrasena = new VentanaCambiarContrasena();

		this.ventanaPartidasGuardadas = new VentanaPartidasGuardadas();
		this.ventanaGuardada = new VentanaGuardada();
		
		
		/* Listeners VentanaInicio */
		this.ventanaInicio.addIniciarSesionListener(new IniciarSesionListener());
		this.ventanaInicio.addAyudaListener(new AyudaListener());
		this.ventanaInicio.addRankingListener(new RankingListener());
		this.ventanaInicio.addRegistrarseListener(new RegistrarseListener());
		this.ventanaInicio.addRecuperarContrasenaListener(new RecuperarContrasenaListener());
		this.ventanaInicio.addJugarListener(new JugarListener());
		
		//this.ventanaInicio.addRecuperarContraseñaListener(new RecuperarContraseñaListener());
		this.ventanaInicio.addCrearConfigListener(new ConfiguracionListener());
		this.ventanaInicio.addSeleccionarConfigListener(new SeleccionConfigListener());

		

		this.ventanaInicio.addContinuarListener(new ContinuarListener());

		/* Listeners VentanaJuego */
		this.ventanaJuego.addJugarTurnoListener(new JugarTurnoListener());
		this.ventanaJuego.addElegirCarta1Listener(new ElegirCarta1Listener());
		this.ventanaJuego.addElegirCarta2Listener(new ElegirCarta2Listener());
		this.ventanaJuego.addElegirCarta3Listener(new ElegirCarta3Listener());
		this.ventanaJuego.addElegirCarta4Listener(new ElegirCarta4Listener());
		this.ventanaJuego.addSiguienteListener(new SiguienteListener());
		this.ventanaJuego.addCambiarContrasenaListener(new cambiarContrasenaListener());
		this.ventanaJuego.addUsarAyuda(new UsarAyudaListener());
		this.ventanaJuego.addGuardarPartida(new GuardarListener());
		

		
		this.ventanaJuego.desactivarBotonJugarTurno();
		this.ventanaJuego.desactivarBotonSiguiente();
		
		/* Listeners VentanaCambiarContrasena */
		this.ventanaCambiarContrasena.addCambiarListener(new cambiarListener());
		
		this.miGestorUsuarios.registrarse(usuarioAct, "123");
	}
	public int getUsos(){
		return usos;
	}
	
	public void sumarUso(){
		this.usos = this.usos +1;
	}
	public static Controlador getMiControlador() {
        if (miControlador == null) {
        	miControlador = new Controlador();
        }
        return miControlador;
    }
	
	public String getUsuarioAct(){
		return this.usuarioAct;
	}
	
	public void setUsuarioAct(String pUsuario){
		this.usuarioAct = pUsuario;
	}
	
	public void crearConf(ArrayList<String> pImagenes,  ArrayList<Integer> pNumeros, String pNombre, String pDesc, String pUs) throws SQLException{
		
		miGestorConfig.crearConf(pImagenes, pNumeros, pNombre, pDesc, pUs);
	}
	
	public int numConfUsuarioAct(){
		Usuario usAct = miGestorUsuarios.buscarUsuario(usuarioAct);
		return usAct.getNumConfig();
	}
	
	public void iniciarAplicacion() {
		this.mostrarVentanaInicio();
	}
	
	public void mostrarVentanaInicio() {
		this.ventanaInicio.setVisible(true);
	}
	
	private void mostrarVentanaJuego() {
		this.ventanaJuego.setVisible(true);
	}

	private void mostrarVentanaAyuda(){
	    this.ventanaAyuda.setVisible(true);
    }

	
	private void mostrarVentanaRanking(){
        this.ventanaRanking.setVisible(true);
    }
	
	private void configUsuario() throws SQLException {
		
		this.ventanaSeleccionConfig.configUsuario(GestorBD.getGestorBD().execSql("SELECT * FROM ConfiguracionUs WHERE idUs='"+usuarioAct+"'"));
		System.out.println("AQUÍ ENTRO");
	}
	
	private void mostrarVentanaSeleccionConfig(){		
        this.ventanaSeleccionConfig.setVisible(true);
    }
	
	private void mostrarVentanaConfiguracion(){
		this.ventanaConfiguracion.setVisible(true);
	}
	
	public void actualizarPartidas() {
		this.ventanaPartidasGuardadas.actualizarPartidas(misGestorBD.obtenerPartidas());		
	}
	
	public void mostrarVentanaPartidas(){
        this.ventanaPartidasGuardadas.setVisible(true);
    }
	
	public void guardarPartida() {
		misGestorBD.obtenerInfoyGuardarPartida();
		JOptionPane.showMessageDialog(null, "Su partida ha sido guardada con exito.");
	}
	
	private void mostrarVentanaGuardado(){
        this.ventanaGuardada.setVisible(true);
    }
	
	private void setUpObservers() {
		ArrayList<Jugador> jugadores = this.partida.obtenerJugadores();
		for(Jugador jug : jugadores) {
			jug.anadirObservador(ventanaJuego);
			/* Notificacion artificial para el inicio de la partida
			 * los jugadores no eran observados mientras se repartian
			 * las cartas. */
			jug.notificar(jug.obtenerInformacionCartas());
		}
		tablero.addObserver(ventanaJuego);
		partida.addObserver(ventanaJuego);
	}
	
	
	class IniciarSesionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String user = ventanaInicio.getTextUsuario();
			String pass = ventanaInicio.getTextContrasena();
			
			if ((user.length() <= 0) || (pass.length() <= 0)) {
				JOptionPane.showMessageDialog(null, "Introduce usuario y contraseña primero");
			}else {
				if(miGestorUsuarios.comprobarLogin(user, pass)) {
					usuarioAct = user;
					ventanaInicio.activarBotonContinuarPartida();
					ventanaInicio.activarBotonJugar();
					ventanaInicio.desactivarBotonIniciarSesion();
					JOptionPane.showMessageDialog(null, user + ", has iniciado sesión");
				}
				else{ ventanaInicio.showNombreErrorMessage();}
				
				/*if(partida.obtenerJugadorReal().getAyudas()==0){
					ventanaJuego.desactivarBotonUsarAyuda();
				}else{
					ventanaJuego.activarBotonUsarAyuda();
				}*/
			}
		}

		
	}
	
	private void ocultarVentanaInicio() {
		ventanaInicio.setVisible(false);
		
	}
	
	class JugarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ocultarVentanaInicio();
			mostrarVentanaJuego();
			partida.inicializarPartida(usuarioAct);
			setUpObservers();
			try {
				partida.obtenerJugadorReal().cargarAyuda();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(partida.obtenerJugadorReal().getAyudas()==0){
				ventanaJuego.desactivarBotonUsarAyuda();
			}else{
				ventanaJuego.activarBotonUsarAyuda();
			}
		}

	}
	
	class RegistrarseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String user = ventanaInicio.getTextUsuario();
			String pass = ventanaInicio.getTextContrasena();
			
			if ((user.length() <= 0) || (pass.length() <= 0)) {
				JOptionPane.showMessageDialog(null, "Introduce usuario y contraseña primero");
			}else {
			miGestorUsuarios.registrarse(user, pass);
			GestorBD.getGestorBD().introducirUsuario(user, pass);
			
			JOptionPane.showMessageDialog(null, user + ", te has registrado con exito");
		
			}
		}
	}
		
	
	class RecuperarContrasenaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String user = ventanaInicio.getTextUsuario();
			
			if (user.length() <= 0) {
				JOptionPane.showMessageDialog(null, "Introduce tu usuario primero");
			}else {
			String nuevaPass = miGestorUsuarios.recuperarContrasena(user);
			if (nuevaPass == null){
				JOptionPane.showMessageDialog(null, "Ese usuario no existe");
			}else {
			JOptionPane.showMessageDialog(null, user + ", tu nueva contrasena es " + nuevaPass);
			}
		}
	}
	}	
	
	class GuardarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			guardarPartida();
			
		}
	}
	
	class ContinuarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			actualizarPartidas();
			mostrarVentanaPartidas();
		}
	}
	
	class AyudaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		    mostrarVentanaAyuda();
		}
	}
	
	class ConfiguracionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		    mostrarVentanaConfiguracion();
		}
	}
	
	class RankingListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		    mostrarVentanaRanking();
		}
	}
	
	class SeleccionConfigListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				configUsuario();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    mostrarVentanaSeleccionConfig();
		}

	}
	
	class ElegirCarta1Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			partida.obtenerJugadorTurnoActual().elegirCartaMano(0);
			ventanaJuego.desactivarBotonesElegir();
			ventanaJuego.desactivarBotonSiguiente();
			ventanaJuego.activarBotonJugarTurno();
		}
	}
	
	class ElegirCarta2Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			partida.obtenerJugadorTurnoActual().elegirCartaMano(1);
			ventanaJuego.desactivarBotonesElegir();
			ventanaJuego.desactivarBotonSiguiente();
			ventanaJuego.activarBotonJugarTurno();
		}
	}
	
	class ElegirCarta3Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			partida.obtenerJugadorTurnoActual().elegirCartaMano(2);
			ventanaJuego.desactivarBotonesElegir();
			ventanaJuego.desactivarBotonSiguiente();
			ventanaJuego.activarBotonJugarTurno();
		}
	}
	
	class ElegirCarta4Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			partida.obtenerJugadorTurnoActual().elegirCartaMano(3);
			ventanaJuego.desactivarBotonesElegir();
			ventanaJuego.desactivarBotonSiguiente();
			ventanaJuego.activarBotonJugarTurno();
		}
	}
	
	class JugarTurnoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			partida.jugarTurno();				
			
			ventanaJuego.desactivarBotonJugarTurno();
			ventanaJuego.activarBotonSiguiente();
		}
	}
	
	class UsarAyudaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Controlador.getMiControlador().sumarUso();
			partida.obtenerJugadorReal().usarAyuda();
			partida.getMiPartida().ayudaUsada();
			ventanaJuego.desactivarBotonUsarAyuda();
			ventanaJuego.activarBotonesElegir();
			if(partida.obtenerJugadorReal().getAyudas()==0){
				JOptionPane.showMessageDialog(null, "Ayuda utilizada. No te quedan mï¿½s ayudas.");
			}
			
			//ventanaJuego.activarBotonSiguiente();
			
		}
	}
	
	class SiguienteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(partida.obtenerJugadorReal().getAyudas()==0){
				partida.obtenerJugadorTurnoActual().elegirCartaMano(0);
				partida.jugarTurno();
				
				ventanaJuego.activarBotonesElegir();
				//ventanaJuego.activarBotonUsarAyuda();
				ventanaJuego.desactivarBotonJugarTurno();
				ventanaJuego.desactivarBotonSiguiente();
				ventanaJuego.desactivarBotonUsarAyuda();

			}else{
				partida.obtenerJugadorTurnoActual().elegirCartaMano(0);
				partida.jugarTurno();
			
				ventanaJuego.activarBotonesElegir();
				ventanaJuego.activarBotonUsarAyuda();
				ventanaJuego.desactivarBotonJugarTurno();
				ventanaJuego.desactivarBotonSiguiente();
			}
		}
	}
	
	class cambiarContrasenaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ventanaCambiarContrasena = new VentanaCambiarContrasena();
			ventanaCambiarContrasena.setVisible(true);
		}
	}
	
	
	//listener de ventanacambiarcontraseï¿½a
	
	class cambiarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("asd");
			String pass = ventanaCambiarContrasena.getTextNContrasena();
			
			if (pass.length() > 0) {
				miGestorUsuarios.cambiarContrasena(usuarioAct, pass);
				ventanaCambiarContrasena.setVisible(false);
			}else {
				JOptionPane.showMessageDialog(null, "Introduce una contrasena");
			}
		}
	}

	public static Carta buscarCarta(int nCarta) {
		
		return null;
	}
	
	public void elegirConfig(String pUs, int pIdConfig) throws SQLException{
		
		miGestorConfig.instanciarConfig(pIdConfig, pUs);
		configAct = pIdConfig;
		
		
	}

	public void anadirConf(ConfiguracionUs cF) {
		Usuario usAct = miGestorUsuarios.buscarUsuario(usuarioAct);
		usAct.anadirConf(cF);
		
	}
	public boolean comprobarConf(int pIdConfig, String pUs) {
		Usuario usAct = miGestorUsuarios.buscarUsuario(usuarioAct);
		
		return usAct.comprobarConf(pIdConfig);
		
	}
	public int getConfAct() {
		
		return configAct;
	}
	public String getImagenConfig(int numeroCartaActual) {
		
		return miGestorConfig.getGestorConfig().imagenConfig(numeroCartaActual,configAct);
	}
}