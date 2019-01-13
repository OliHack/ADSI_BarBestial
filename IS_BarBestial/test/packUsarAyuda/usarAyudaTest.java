package packUsarAyuda;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import packModelo.Animal;
import packModelo.Bar;
import packModelo.Camaleon;
import packModelo.Canguro;
import packModelo.Carta;
import packModelo.Cocodrilo;
import packModelo.EnumColor;
import packModelo.Jugador;
import packModelo.JugadorReal;
import packModelo.Leon;
import packModelo.Loro;
import packModelo.Mofeta;
import packModelo.Mono;
import packModelo.Partida;
import packModelo.Serpiente;
import packModelo.Tablero;

public class usarAyudaTest {
	Jugador jugador;
    Carta carta1, carta2, carta3, carta4, carta5, carta6, carta7, carta8, carta9;
    Animal camaleon, leonA, mofetaV, mofetaA, loro, serpienteA, mono, cocodriloV, canguroV;
    Tablero t = Tablero.getMiTablero();
    Bar b = Bar.getMiBar();;
    Partida part = Partida.getMiPartida();
    		
	@Before
	public void setUp() throws Exception {		
		
        camaleon = new Camaleon();
        leonA = new Leon();
        mofetaV = new Mofeta();
        mofetaA = new Mofeta();
        loro = new Loro();
        serpienteA = new Serpiente();
        mono = new Mono();
        cocodriloV = new Cocodrilo();    
        canguroV = new Canguro();
        		
        carta1 = new Carta(camaleon, EnumColor.VERDE);
        carta2 = new Carta(leonA, EnumColor.AZUL);
        carta3 = new Carta(mofetaV, EnumColor.VERDE);
        carta4 = new Carta(mofetaA, EnumColor.AZUL);
        carta5 = new Carta(loro, EnumColor.VERDE);
        carta6 = new Carta(serpienteA, EnumColor.AZUL);
        carta7 = new Carta(mono, EnumColor.AZUL);
        carta8 = new Carta(cocodriloV, EnumColor.VERDE);
        carta9 = new Carta(canguroV, EnumColor.VERDE);
        
        b.anadirCarta(carta1);
        b.anadirCarta(carta2);
        
        t.anadirALaCola(carta2);
        t.anadirALaCola(carta8);
        t.anadirALaCola(carta9);
        t.anadirALaCola(carta6);
        
        jugador = new JugadorReal("Unai", EnumColor.AZUL,4);
        //System.out.println(jugador.getAyudas());
		jugador.getManoJugador().anadirCarta(carta4);
		jugador.getMazoJugador().anadirCarta(carta7);
		
		
		part.addJugador(jugador);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void inicializar() {
		testAyudaSinCartasEnElBar();  
		//testAyuda2SinCartasEnElBar();
		//testAyudaConCartasEnElBar();
	}
	
	public void testAyudaSinCartasEnElBar(){
		//No hay cartas en el bar
		//Utiliza un ayuda
		b.getLista().vaciar();
		jugador.usarAyuda();
		assertEquals(3,jugador.getAyudas());
			
	}
	
	public void testAyuda2SinCartasEnElBar(){
		// No hay cartas en el bar
		// Utiliza 2 ayudas
		b.getLista().vaciar();
		jugador.usarAyuda();
		jugador.usarAyuda();
		assertEquals(2,jugador.getAyudas());
		
	}
	
	public void testAyudaConCartasEnElBar(){
		//Hay cartas en el bar
		//Pulsa una vez el botón
		jugador.usarAyuda();
		assertEquals(3,jugador.getAyudas());
	}
		
	public void testAyuda2ConCartasEnElBar(){
		//Hay cartas en el bar
		//Pulsa dos veces el botón
		jugador.usarAyuda();
		jugador.usarAyuda();
		assertEquals(3,jugador.getAyudas());
	}
}

