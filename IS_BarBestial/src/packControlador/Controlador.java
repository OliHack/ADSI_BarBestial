package packControlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import packModelo.GestorBD;
import packModelo.Carta;
import packModelo.GestorCartas;
import packModelo.GestorConfiguraciones;
import packModelo.GestorUsuarios;
import packModelo.Jugador;
import packModelo.Partida;
import packModelo.RankingDB;
import packModelo.Tablero;
import packVista.VentanaAyuda;
import packVista.VentanaInicio;
import packVista.VentanaJuego;
import packVista.VentanaRanking;
import packVista.VentanaSeleccionConfig;

public class Controlador {
	private static Controlador miControlador;
	private String usuarioAct;
	
	/* Modelo */
	private Partida partida;
	private Tablero tablero;
	private RankingDB rankingDB;
	private Jugador jug;

	private GestorConfiguraciones miGestorConfig;
	private GestorCartas miGestorCartas;
	private GestorUsuarios miGestorUsuarios;
	private GestorBD misGestorBD;

	
	/* Vista */
	private VentanaInicio ventanaInicio;
	private VentanaJuego ventanaJuego;
	private VentanaAyuda ventanaAyuda;
	private VentanaRanking ventanaRanking;
	private VentanaSeleccionConfig ventanaSeleccionConfig;
	
	public Controlador() {
		this.usuarioAct = null;
		this.partida = Partida.getMiPartida();
		this.tablero = Tablero.getMiTablero();
		this.rankingDB = RankingDB.getRankingDB();
		this.miGestorConfig = GestorConfiguraciones.getGestorConfig();
		this.miGestorCartas = GestorCartas.getGestorCartas();
		this.miGestorUsuarios = GestorUsuarios.getGestorUsuarios();
		this.misGestorBD = GestorBD.getGestorBD();
		
		this.ventanaInicio = new VentanaInicio();
		this.ventanaJuego = new VentanaJuego();
		this.ventanaAyuda = new VentanaAyuda();
		this.ventanaRanking = new VentanaRanking();
		this.ventanaSeleccionConfig = new VentanaSeleccionConfig();

		
		/* Listeners VentanaInicio */
		this.ventanaInicio.addJugarListener(new JugarListener());
		this.ventanaInicio.addAyudaListener(new AyudaListener());
		this.ventanaInicio.addRankingListener(new RankingListener());
		this.ventanaInicio.addSeleccionarConfigListener(new SeleccionConfigListener());
		
		/* Listeners VentanaJuego */
		this.ventanaJuego.addJugarTurnoListener(new JugarTurnoListener());
		this.ventanaJuego.addElegirCarta1Listener(new ElegirCarta1Listener());
		this.ventanaJuego.addElegirCarta2Listener(new ElegirCarta2Listener());
		this.ventanaJuego.addElegirCarta3Listener(new ElegirCarta3Listener());
		this.ventanaJuego.addElegirCarta4Listener(new ElegirCarta4Listener());
		this.ventanaJuego.addSiguienteListener(new SiguienteListener());
		this.ventanaJuego.addUsarAyuda(new UsarAyudaListener());
		
		this.ventanaJuego.desactivarBotonJugarTurno();
		this.ventanaJuego.desactivarBotonSiguiente();
		
		
		
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
	
	private void mostrarVentanaJuego() {
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
		
		this.ventanaSeleccionConfig.configUsuario(GestorBD.execSql("SELECT idConfig,nombre,fecha FROM ConfiguracionUs WHERE idUs="+usuarioAct));	
	}
	
	private void mostrarVentanaSeleccionConfig(){
		
        this.ventanaSeleccionConfig.setVisible(true);
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
	
	
	class JugarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String nombre = ventanaInicio.getTextFieldNombreValue();
			
			if(nombre.length() > 0) {
				//ocultarVentanaInicio();
				mostrarVentanaJuego();
				partida.inicializarPartida(nombre);;
				setUpObservers();
			}			
			else ventanaInicio.showNombreErrorMessage();			
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
			jug.usarAyuda();
		}
	}
	
	class SiguienteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			partida.obtenerJugadorTurnoActual().elegirCartaMano(0);
			partida.jugarTurno();
			
			ventanaJuego.activarBotonesElegir();
			ventanaJuego.desactivarBotonJugarTurno();
			ventanaJuego.desactivarBotonSiguiente();
		}
	}

	public static Carta buscarCarta(int nCarta) {
		
		return null;
	}
}