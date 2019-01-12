package packModelo;

import java.util.ArrayList;
import java.util.Date;

public class ConfiguracionUs {

	private String nombre;
	private String desc;
	private String fecha;
	private int idConfig;
	private ArrayList<ConfiguracionCarta> listaConfiguracionesCarta;
	
	public ConfiguracionUs(String nombre, String desc, String fecha, int idConfig){
		this.nombre = nombre;
		this.desc = desc;
		this.fecha = fecha;
		this.idConfig = idConfig;
	}
	


	public String getNombre(){
		return nombre;
	}
	
	public String getDesc(){
		return desc;
	}
	
	public String getFecha(){
		return fecha;
	}
	
	public int getIdConfig(){
		return idConfig;
	}
	
	private void anadirConfiguracionCarta(ConfiguracionCarta cfC){
		listaConfiguracionesCarta.add(cfC);
	}
	
	public void nuevaConfiguracionCarta(String nombreImg, int carta){
		ConfiguracionCarta cC = new ConfiguracionCarta(nombreImg, this.idConfig, carta);
		anadirConfiguracionCarta(cC);
	}



	public String getImagenPos(int numeroCartaActual) {
		String imagen = null;
		int i=0;
		while(i<listaConfiguracionesCarta.size()){
			if(listaConfiguracionesCarta.get(i).getCarta()==numeroCartaActual){
				imagen = listaConfiguracionesCarta.get(i).getImagen();
				i=200;	
			}
			
		}
		return imagen;
	}
	
}
