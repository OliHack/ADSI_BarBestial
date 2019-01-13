package packModelo;

import java.util.ArrayList;
import java.util.UUID;

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
	
	public ArrayList<String> GetAllIDs(){
		
		ArrayList<String> nombres = new ArrayList<String>();
		
		for (int i = 0; i < this.listaUsuarios.size(); i++) {
			nombres.add(this.listaUsuarios.get(i).getIdUsuario());
		}
		return nombres;
	}
	
	public void anadirConfiguracion(ConfiguracionUs pConfig, Usuario user) {
		user.anadirConf(pConfig);
	}
	
	public Usuario buscarUsuario(String pNomUsuario) {
		String compUsuario = "";
		for (int i = 0; i < this.listaUsuarios.size(); i++) {
			compUsuario = this.listaUsuarios.get(i).getIdUsuario();
			if (compUsuario.equals(pNomUsuario)) {
				return listaUsuarios.get(i);
			}
		}
		return null;
	}
	
	public boolean comprobarLogin(String user, String pass) {
		if (this.buscarUsuario(user).getContrasena().equals(pass)) {return true;}
		else{return false;}
	}
	
	public String recuperarContrasena(String user) {
		Usuario userEncontrado = buscarUsuario(user);
		if (userEncontrado != null) {
			String uuid = UUID.randomUUID().toString();
			userEncontrado.setPassword(uuid);
			return uuid;
		}
		return null;
	}
	
	public void cambiarContrasena(String user, String nueva) {
		this.buscarUsuario(user).setPassword(nueva);
	}
	
	public void registrarse(String user, String pass) {
		this.listaUsuarios.add(new Usuario(user, pass));

	}
}
