package packModelo;

import java.util.ArrayList;

public class Usuario {
	
	String nombre;
	ArrayList<ConfiguracionUs> listaConfiguraciones;

	public String getNombre() {
		
		return nombre;
	}
	
	public int getNumConfig(){
		return listaConfiguraciones.size();
	}

	public void añadirConf(ConfiguracionUs cF) {
		listaConfiguraciones.add(cF);
		
	}

}
