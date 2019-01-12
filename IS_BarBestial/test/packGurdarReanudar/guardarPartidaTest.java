package packGurdarReanudar;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import packControlador.Controlador;
import packModelo.*;


public class guardarPartidaTest {
	Jugador jugador, maquina;
    Carta carta1, carta2, carta3, carta4, carta5, carta6, carta7, carta8;
    Animal camaleon, leon, mofetaV, mofetaA, loro, serpiente, mono, cocodrilo;
    Tablero t = Tablero.getMiTablero();
    Bar b = Bar.getMiBar();
    EsLoQueHay e = EsLoQueHay.getMiEsLoQueHay();
    Partida part = Partida.getMiPartida();
    
    Controlador cont = Controlador.getMiControlador();
    GestorBD SGBD = GestorBD.getGestorBD();
    
    @Before
	public void setUp() throws Exception {		
		
        camaleon = new Camaleon();
        leon = new Leon();
        mofetaV = new Mofeta();
        mofetaA = new Mofeta();
        loro = new Loro();
        serpiente = new Serpiente();
        mono = new Mono();
        cocodrilo = new Cocodrilo();       

        carta1 = new Carta(camaleon, EnumColor.VERDE);
        carta2 = new Carta(leon, EnumColor.AZUL);
        carta3 = new Carta(mofetaV, EnumColor.VERDE);
        carta4 = new Carta(mofetaA, EnumColor.AZUL);
        carta5 = new Carta(loro, EnumColor.VERDE);
        carta6 = new Carta(serpiente, EnumColor.AZUL);
        carta7 = new Carta(mono, EnumColor.VERDE);
        carta8 = new Carta(cocodrilo, EnumColor.AZUL);
        
        b.anadirCarta(carta2);
        
        t.anadirALaCola(carta3);
        
        e.anadirCarta(carta4);
        
        jugador = new JugadorReal("Jonan", EnumColor.AZUL,2);
		maquina = new Maquina("Maquina", EnumColor.VERDE,0);
		
		jugador.getMano().anadirCarta(carta6);
		jugador.getMazo().anadirCarta(carta7);
		
		maquina.getMano().anadirCarta(carta8);
		maquina.getMazo().anadirCarta(carta5);
		
		
		part.anadirJugador(jugador);
		part.anadirJugador(maquina);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGuardarPartida() {
		/**
		 * Para la ejecución de estas pruebas se deberá de borrar la Base de Datos para que no haya ninguna tupla
		 * 		en la tabla Partida.
		 * 
		 * Se introducirá 1 Partida y se comprobará si la info en la base de datos coincide con la info
		 * 		almacenada previamente.
		 */
		cont.guardarPartida();
		int numPart = SGBD.contarPartidas();
		assertEquals(1,numPart);
		
		Vector<String> info = SGBD.infoPartidas();
		
		
		//Prueba ayUsuario
		String ayUs = info.get(0);
		int ayUsuario = Integer.parseInt(ayUs);
		assertEquals(2,ayUsuario);
		
		
		//Prueba turno
        String turno = info.get(1);
        int turnoAct = Integer.parseInt(turno);
        assertEquals(0,turnoAct);
        
        
        //Prueba manoMaq
        String manoMaq = info.get(2);
        int num = getNumCarta(manoMaq, 0);
        assertEquals(maquina.getMano().obtenerCartaEnPosicion(0).getFuerza(),num);
        
        
        //Prueba manoUs
        String manoUs = info.get(3);
        num = getNumCarta(manoUs, 0);
        assertEquals(jugador.getMano().obtenerCartaEnPosicion(0).getFuerza(),num);
        
        
        //Prueba calle
        String calle = info.get(4);
        ArrayList<String> res = getNumColorCarta(calle,0);
        num = Integer.parseInt(res.get(0));
        String color = res.get(1);
      
        assertEquals(e.getCalle().obtenerCartaEnPosicion(0).getFuerza(),num);
        assertEquals(e.getCalle().obtenerCartaEnPosicion(0).getColor().toString(),color.replaceAll("/", ""));
        
        //Prueba bar
        String bar = info.get(5);
        res = getNumColorCarta(bar,0);
        num = Integer.parseInt(res.get(0));
        color = res.get(1);
        assertEquals(b.getLista().obtenerCartaEnPosicion(0).getFuerza(),num);
        assertEquals(b.getLista().obtenerCartaEnPosicion(0).getColor().toString(),color.replaceAll("/", ""));
        
        //Prueba mazoMaq
        String mazoMaq = info.get(6);
        num = getNumCarta(mazoMaq, 0);
        assertEquals(maquina.getMazo().obtenerCartaEnPosicion(0).getFuerza(),num);
        
        
        //Prueba mazoUs
        String mazoUs = info.get(7);
        num = getNumCarta(mazoUs, 0);
        assertEquals(jugador.getMazo().obtenerCartaEnPosicion(0).getFuerza(),num);
        
        
        //Prueba cola
        String cola = info.get(8);
        res = getNumColorCarta(cola,0);
        num = Integer.parseInt(res.get(0));
        color = res.get(1);
        assertEquals(t.getCola().obtenerCartaEnPosicion(0).getFuerza(),num);
        assertEquals(t.getCola().obtenerCartaEnPosicion(0).getColor().toString(),color.replaceAll("/", ""));
        
        //Prueba nombre Usuario
        String idUs = info.get(9);
        assertEquals(jugador.getNombre(),idUs);
        
	}
	
	private int getNumCarta(String lista, int pos) {
		String[] parts = lista.split("/");
		int num = Integer.parseInt(parts[pos]);
		return num;
	}
	
	private ArrayList<String> getNumColorCarta(String lista, int pos) {
		ArrayList<String> res = new ArrayList<String>();
		String[] parts = lista.split("/");
		String info = parts[pos];
		
		String[] parts2 = lista.split("-");
		String num =  parts2[0];
		String color = parts2[1];		
		
		res.add(num);
		res.add(color);
		return res;
	}
}
