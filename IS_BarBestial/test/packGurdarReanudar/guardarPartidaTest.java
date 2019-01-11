package packGurdarReanudar;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        
        b.anadirCarta(carta1);
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
	public void test() {
		cont.guardarPartida();
		
		
		
		//assertEquals(2, jugador.getNumAyudas());
	}

}
