package packModelo;

public class ConfiguracionCarta {

	
	private String imagen;
	private int idConfig;
	private int carta;
	
	public ConfiguracionCarta(String imagen, int idConfig, int carta){
		this.imagen = imagen;
		this.idConfig = idConfig;
		this.carta = carta;
	}
	
	
	public String getImagen(){
		return imagen;
	}
	
	public int getIdConfig(){
		return idConfig;
	}
	
	public int getCarta(){
		return carta;
	}
}
