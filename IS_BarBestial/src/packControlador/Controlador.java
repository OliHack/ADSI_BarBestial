package packControlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import packModelo.GestorBD;
import packModelo.Carta;
import packModelo.GestorCartas;
import packModelo.GestorConfiguraciones;
import packModelo.GestorUsuarios;

import packModelo.EnumColor;


import packModelo.Jugador;
import packModelo.JugadorReal;
import packModelo.Partida;
import packModelo.RankingDB;
import packModelo.Tablero;
import packVista.VentanaAyuda;
import packVista.VentanaInicio;
import packVista.VentanaJuego;
import packVista.VentanaRanking;
import packVista.VentanaSeleccionConfig;

import packModelo.*;

import packVista.*;


public class Controlador {
	private static Controlador miControlador;
	private String usuarioAct;
	
	/* Modelo */
	private Partida partida;
	private Tablero tablero;
	private RankingDB rankingDB;
	private Jugador jug;
	private int usos;

	private GestorConfiguraciones miGestorConfig;
	private GestorCartas miGestorCartas;
	private GestorUsuarios miGestorUsuarios;

	private GestorBD misGestorBD;

	private GestorPartida miGestorPartida;


	/* Vista */
	private VentanaInicio ventanaInicio;
	private VentanaJuego ventanaJuego;
	private VentanaAyuda ventanaAyuda;
	private VentanaRanking ventanaRanking;
	private VentanaSeleccionConfig ventanaSeleccionConfig;
	private VentanaPartidasGuardadas ventanaPartidasGuardadas;
	private VentanaGuardada ventanaGuardada;
	private VentanaCambiarContraseña ventanaCambiarContraseña;
	
	public Controlador() {
		this.usuarioAct = null;
		this.partida = Partida.getMiPartida();
		this.tablero = Tablero.getMiTablero();
		this.rankingDB = RankingDB.getRankingDB();
		this.usos = 0;

		this.miGestorConfig = GestorConfiguraciones.getGestorConfig();
		this.miGestorCartas = GestorCartas.getGestorCartas();
		this.miGestorUsuarios = GestorUsuarios.getGestorUsuarios();

		this.misGestorBD = GestorBD.getGestorBD();

		this.miGestorPartida = GestorPartida.getGestorPartida();

		this.ventanaInicio = new VentanaInicio();
		this.ventanaAyuda = new VentanaAyuda();
		this.ventanaRanking = new VentanaRanking();
		this.ventanaSeleccionConfig = new VentanaSeleccionConfig();

		this.ventanaPartidasGuardadas = new VentanaPartidasGuardadas();
		this.ventanaGuardada = new VentanaGuardada();
		
		
		/* Listeners VentanaInicio */
		this.ventanaInicio.addIniciarSesionListener(new IniciarSesionListener());
		this.ventanaInicio.addAyudaListener(new AyudaListener());
		this.ventanaInicio.addRankingListener(new RankingListener());
		this.ventanaInicio.addRegistrarseListener(new RegistrarseListener());
		this.ventanaInicio.addRecuperarContraseñaListener(new RecuperarContraseñaListener());
		
		//this.ventanaInicio.addSeleccionarConfigListener(new SeleccionConfigListener());
		

		this.ventanaInicio.addContinuarListener(new ContinuarListener());

		/* Listeners VentanaJuego */
		this.ventanaJuego.addJugarTurnoListener(new JugarTurnoListener());
		this.ventanaJuego.addElegirCarta1Listener(new ElegirCarta1Listener());
		this.ventanaJuego.addElegirCarta2Listener(new ElegirCarta2Listener());
		this.ventanaJuego.addElegirCarta3Listener(new ElegirCarta3Listener());
		this.ventanaJuego.addElegirCarta4Listener(new ElegirCarta4Listener());
		this.ventanaJuego.addSiguienteListener(new SiguienteListener());
		this.ventanaJuego.addCambiarContraseñaListener(new cambiarContraseñaListener());

		this.ventanaJuego.addUsarAyuda(new UsarAyudaListener());

		this.ventanaJuego.addGuardarPartida(new GuardarListener());
		

		
		this.ventanaJuego.desactivarBotonJugarTurno();
		this.ventanaJuego.desactivarBotonSiguiente();
		
		/* Listeners VentanaCambiarContraseña */
		this.ventanaCambiarContraseña.addCambiarListener(new cambiarListener());
		
		
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
	
	public void iniciarAplicacion() {
		this.mostrarVentanaInicio();
	}
	
	private void mostrarVentanaInicio() {
		this.ventanaInicio.setVisible(true);
	}
	
	private void mostrarVentanaJuego(String user) {
		this.ventanaJuego = new VentanaJuego(user);
		this.ventanaJuego.setVisible(true);
	}

	private void mostrarVentanaAyuda(){
	    this.ventanaAyuda.setVisible(true);
    }
	
	private void actualizarRanking() {
		this.ventanaRanking.actualizarRanking(rankingDB.obtenerMejoresPuntuaciones());		
	}
	
	private void mostrarVentanaRanking(){
        this.ventanaRanking.setVisible(true);
    }
	
	private void configUsuario() throws SQLException {
		
		this.ventanaSeleccionConfig.configUsuario(misGestorBD.execSql("SELECT idConfig,nombre,fecha FROM ConfiguracionUs WHERE idUs="+usuarioAct));	
	}
	
	private void mostrarVentanaSeleccionConfig(){		
        this.ventanaSeleccionConfig.setVisible(true);
    }
	
	private void actualizarPartidas() {
		this.ventanaPartidasGuardadas.actualizarPartidas(misGestorBD.obtenerPartidas());		
	}
	
	private void mostrarVentanaPartidas(){
        this.ventanaPartidasGuardadas.setVisible(true);
    }
	
	public void guardarPartida() {
		misGestorBD.obtenerInfoyGuardarPartida();
		JOptionPane.showMessageDialog(null, "Su partida ha sido guardada con éxito.");
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
			String pass = ventanaInicio.getTextContraseña();
			
			if(miGestorUsuarios.comprobarLogin(user, pass)) {
				//ocultarVentanaInicio();
				mostrarVentanaJuego(user);
				partida.inicializarPartida(user);
				setUpObservers();
				try {
					partida.obtenerJugadorReal().cargarAyuda();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else{ ventanaInicio.showNombreErrorMessage();}
			
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
			String pass = ventanaInicio.getTextContraseña();
			
			miGestorUsuarios.registrarse(user, pass);
			
			JOptionPane.showMessageDialog(null, user + ", te has registrado con éxito");
		}
	}
	
	class RecuperarContraseñaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String user = ventanaInicio.getTextUsuario();
			String nuevaPass = miGestorUsuarios.recuperarContraseña(user);
			JOptionPane.showMessageDialog(null, user + ", tu nueva contraseña es " + nuevaPass);
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
	
	class RankingListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			actualizarRanking();
		    mostrarVentanaRanking();
		}
	}
	
	/*class SeleccionConfigListener implements ActionListener {
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

	}*/
	
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
				JOptionPane.showMessageDialog(null, "Ayuda utilizada. No te quedan más ayudas.");
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
	
	class cambiarContraseñaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ventanaCambiarContraseña = new VentanaCambiarContraseña(ventanaJuego.getUser());
			ventanaCambiarContraseña.setVisible(true);
		}
	}
	
	
	//listener de ventanacambiarcontraseña
	
	class cambiarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String pass = ventanaCambiarContraseña.getTextNContraseña();
			
			if (pass.length() > 0) {
				miGestorUsuarios.cambiarContraseña(ventanaCambiarContraseña.getUser(), pass);
				ventanaCambiarContraseña.setVisible(false);
			}else {
				JOptionPane.showMessageDialog(null, "Introduce una contraseña");
			}
		}
	}

	public static Carta buscarCarta(int nCarta) {
		
		return null;
	}
}