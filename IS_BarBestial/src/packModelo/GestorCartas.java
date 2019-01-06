package packModelo;

import java.util.ArrayList;

public class GestorCartas {

	private static GestorCartas miGestor;
	private ArrayList<Carta> listaCartas;
	
	private GestorCartas(){
		listaCartas = new ArrayList<Carta>();
	}
	
	public static GestorCartas getGestorCartas() {
		if(miGestor == null){
			miGestor = new GestorCartas();
		}
		return miGestor;
	}
}
