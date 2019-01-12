package packModelo;

import java.util.ArrayList;


public class Usuario {
	private String idUsuario;
	String nombre;
	private String password;
	private int numAyudas;
	private ArrayList<ConfiguracionUs> listaConfiguraciones;
	
	public Usuario(String user, String pass){
		this.idUsuario = user;
		this.password = pass;
		this.numAyudas = 0;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public String getContrasena() {
		return password;
	}
	
	public String getNombre() {
		
		return nombre;
	}
	
	public int getNumConfig(){
		return listaConfiguraciones.size();
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean usarAyuda() {
		
		if (this.numAyudas >= 0) {this.numAyudas = this.numAyudas - 1; return true;}
		return false;
	}
	
	public boolean coincide(String pNomUsuario){
		if (this.idUsuario.equals(pNomUsuario)) {return true;}
		else {return false;}
	}

	public void anadirConf(ConfiguracionUs cF) {
		listaConfiguraciones.add(cF);
		
	}

	public boolean comprobarConf(int pIdConfig) {
		int i = 0;
		while(pIdConfig != listaConfiguraciones.get(i).getIdConfig()){
			i++;
		}
		return (pIdConfig == listaConfiguraciones.get(i).getIdConfig());
	}
}
