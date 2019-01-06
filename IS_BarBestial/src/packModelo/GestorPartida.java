package packModelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GestorPartida {
	private ArrayList<Partida> listaPartidas;
	private static GestorPartida miGestor;
	
	private GestorPartida(){
		listaPartidas = new ArrayList<Partida>();
	}
	
	public static GestorPartida getGestorPartida() {
		if(miGestor == null){
			miGestor = new GestorPartida();
		}
		return miGestor;
	}
	
	public void anadirPartida(int pAy, int pPart, boolean turno, String manoMaq, String manoUs, String calle, String bar, String mazoMaq,
							String mazoUs, String cola, int pUs, Date fecha ) throws SQLException{
		
		String consulta = "SELECT * FROM Partida WHERE idPartida="+pPart;
		ResultSet result = GestorBD.execSql(consulta);
		int pIdConf = result.getFetchSize() + 1;
		String strUpdate = "INSERT INTO Partida VALUES ("+pAy+","+pPart+","+turno+","+manoMaq+","+manoUs+","+calle+","+bar+","+mazoMaq+","+mazoUs+","+cola+","+pUs+",GETDATE())";
		GestorBD.sqlUpdate(strUpdate);
		
	}
	
}
