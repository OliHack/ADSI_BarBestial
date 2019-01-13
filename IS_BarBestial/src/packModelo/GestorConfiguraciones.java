package packModelo;

import packControlador.Controlador;
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
		
		String consulta = "SELECT * FROM ConfiguracionUs WHERE idUs='"+pUs+"'";
		ResultSet result = GestorBD.getGestorBD().execSql(consulta);
		int pIdConf = 1;
		while(result.next()){
			pIdConf++;
		}

		String strUpdate = "INSERT INTO ConfiguracionUs VALUES ("+pIdConf+",'"+pNombre+"','"+ pDesc +"',datetime('now'),'"+pUs+"')";
		GestorBD.getGestorBD().sqlUpdate(strUpdate);
		
		for(int i=0; i<pImagenes.size(); i++){
			int pNum = pNumeros.get(i);
			String pImg = pImagenes.get(i);
			String strUpdate2 = "INSERT INTO ConfiguracionCarta VALUES ("+pIdConf+","+ pNum +",'"+pImg+"')";
			GestorBD.getGestorBD().sqlUpdate(strUpdate2);
		}
	}
	
	public void instanciarConfig(int pIdConfig, String pUs) throws SQLException{
		

		if(!Controlador.getMiControlador().comprobarConf(pIdConfig,pUs)){
			String consulta1 = "SELECT * FROM ConfiguracionUs WHERE idUs='"+pUs+"' AND idConfig="+pIdConfig;
			ResultSet rs = GestorBD.getGestorBD().execSql(consulta1);
			rs.next();
			int idConfig = rs.getInt(1);
			String nombre = rs.getString(2);
			String desc = rs.getString(3);
			String fecha = rs.getString(4);
			
			ConfiguracionUs cF = new ConfiguracionUs(fecha,nombre,desc,idConfig);
			listaConfiguraciones.add(cF);
			
			Controlador.getMiControlador().anadirConf(cF);
			
			String consulta2 = "SELECT * FROM ConfiguracionCarta WHERE idConfig="+pIdConfig;
			ResultSet rs1 = GestorBD.getGestorBD().execSql(consulta2);
			
			while(rs1.next()){
				int nCarta = Integer.parseInt(rs1.getString(2));
				String nImg = rs1.getString(3);
				cF.nuevaConfiguracionCarta(nImg, nCarta);
				
			}
		}
		
		
	}

	public String imagenConfig(int numeroCartaActual, int configAct) {
		
		
		for (int i = 0; i < this.listaConfiguraciones.size(); i++) {
			if (listaConfiguraciones.get(i).getIdConfig()==configAct) {
				return listaConfiguraciones.get(i).getImagenPos(numeroCartaActual);
			}
		}
		return null;
	}

	
}
