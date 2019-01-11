package packModelo;

import java.util.ArrayList;

public class Usuario {
	private String idUsuario;
	private String password;
	private int numAyudas;
	private ArrayList<ConfiguracionCarta> ConfiguracionCarta;
	
	public Usuario(String user, String pass){
		this.idUsuario = user;
		this.password = pass;
		this.numAyudas = 0;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public String getContraseña() {
		return password;
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

	public void addConfig(ConfiguracionCarta pConfig) {
		this.ConfiguracionCarta.add(pConfig);
	}
}
