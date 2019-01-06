package packModelo;

import java.util.ArrayList;

public class GestorUsuarios {
	
	private static GestorUsuarios miGestor;
	private ArrayList<Usuario> listaUsuarios;
	
	private GestorUsuarios(){
		listaUsuarios= new ArrayList<Usuario>();
	}
	
	public static GestorUsuarios getGestorUsuarios() {
		if(miGestor == null){
			miGestor = new GestorUsuarios();
		}
		return miGestor;
	}
}
