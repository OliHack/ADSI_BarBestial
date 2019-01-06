package packModelo;

import packControlador.Controlador;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestorConfiguraciones {

	private ArrayList<ConfiguracionUs> listaConfiguraciones;
	private static GestorConfiguraciones miGestor;
	
	private GestorConfiguraciones(){
		listaConfiguraciones = new ArrayList<ConfiguracionUs>();
	}
	
	public static GestorConfiguraciones getGestorConfig() {
		if(miGestor == null){
			miGestor = new GestorConfiguraciones();
		}
		return miGestor;
	}
	
	public void crearConf(ArrayList<String> pImagenes, ArrayList<Integer> pNumeros, String pNombre, String pDesc, String pUs) throws SQLException{
		String consulta = "SELECT * FROM ConfiguracionUs WHERE idUsuario="+pUs;
		ResultSet result = GestorBD.execSql(consulta);
		int pIdConf = result.getFetchSize() + 1;
		String strUpdate = "INSERT INTO ConfiguracionUs VALUES ("+pIdConf+","+pNombre+","+ pDesc +",GETDATE(),"+pUs+")";
		GestorBD.sqlUpdate(strUpdate);
		
		for(int i=0; i<pImagenes.size(); i++){
			int pNum = pNumeros.get(i);
			String pImg = pImagenes.get(i);
			String strUpdate2 = "INSERT INTO ConfiguracionCarta VALUES ("+pIdConf+","+ pNum +","+pImg+")";
		}
	}
	
	public void instanciarConfig(int pIdConfig, String pUs) throws SQLException{
		String consulta1 = "SELECT * FROM ConfiguracionUs WHERE idUsuario="+pUs+" AND idConfig="+pIdConfig;
		ResultSet rs = GestorBD.execSql(consulta1);
		rs.next();
		int idConfig = rs.getInt(1);
		String nombre = rs.getString(2);
		String desc = rs.getString(3);
		String fecha = rs.getString(4);
		
		ConfiguracionUs cF = new ConfiguracionUs(fecha,nombre,desc,idConfig);
		
		String consulta2 = "SELECT * FROM ConfiguracionCarta WHERE idConfig="+pIdConfig;
		ResultSet rs1 = GestorBD.execSql(consulta2);
		
		while(rs1.next()){
			int nCarta = Integer.parseInt(rs1.getString(2));
			String nImg = rs1.getString(3);
			Carta ct = Controlador.buscarCarta(nCarta);
			cF.nuevaConfiguracionCarta(nImg, ct);
			
		}
		
		
		
	}

	
}
