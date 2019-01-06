package packModelo;

public class ConfiguracionCarta {

	
	private String imagen;
	private int idConfig;
	private Carta carta;
	
	public ConfiguracionCarta(String imagen, int idConfig, Carta carta){
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
	
	public Carta getCarta(){
		return carta;
	}
}
