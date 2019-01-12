package packGurdarReanudar;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import packControlador.Controlador;
import packModelo.*;
import packVista.*;

public class reanudarPartidaTest {
    
    Controlador cont = Controlador.getMiControlador();
    GestorBD SGBD = GestorBD.getGestorBD();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIntroducirYMostrar() {
		/**
		 * Para la ejecución de estas pruebas se deberá de borrar la Base de Datos para que no haya ninguna tupla
		 * 		en la tabla Partida.
		 * 
		 * Se introducirán Partidas de 1 en 1 y se comprobará que la base de datos se actualiza.
		 */
		
		//La prueba comienza introduciendo una Partida
			SGBD.insertarPartidaPrueba();
			int numPart = SGBD.contarPartidas();
		
		//Por lo que, en la base de datos deberá figurar que existen 1 partidas.
			assertEquals(1,numPart);

			
		//Introducimos otra Partida
			SGBD.insertarPartidaPrueba();
			numPart = SGBD.contarPartidas();
		
		//Por lo que, en la base de datos ahora deberá figurar que existen 2 partidas.
			assertEquals(2,numPart);
			
			
		//Introducimos otra Partida
			SGBD.insertarPartidaPrueba();
			numPart = SGBD.contarPartidas();
		
		//Por lo que, en la base de datos ahora deberá figurar que existen 3 partidas.
			assertEquals(3,numPart);
	}

}
