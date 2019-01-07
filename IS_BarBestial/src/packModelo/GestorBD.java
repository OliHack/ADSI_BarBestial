package packModelo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class GestorBD {
	
	
		private static GestorBD miGestorBD;
	    private static Connection c;
	    private static Statement s;

	    
	    private GestorBD() {
	        File f = new File("DataBase.db");
	        if (!f.exists()) {
	            this.crearBaseDatos();
	            this.crearTablaPuntuaciones();
	        }
	    }
	    
	    
	    public static GestorBD getGestorBD() {
	        if (miGestorBD == null) {
	            miGestorBD = new GestorBD();
	        }
	        return miGestorBD;
	    }

	    private void crearBaseDatos() {
	        try {
	            Class.forName("org.sqlite.JDBC");
	            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
	        } catch (Exception e) {
	            System.err.println(e.getClass().getName() + ": " + e.getMessage());
	            System.exit(0);
	        }
	        System.out.println("Base de datos creada");
	    }

	    private void crearTablaPuntuaciones() {
	        try {
	            Class.forName("org.sqlite.JDBC");
	            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");

	            s = c.createStatement();
	            String instruccion = "CREATE TABLE ConfiguracionUs " +
	                    "(idConfig	INT    NOT NULL, " +
	                    "nombre		VARCHAR(20) NOT NULL, " +
	                    " desc		VARCHAR(20) NOT NULL, " +
	                    " fecha	    DATETIME    NOT NULL, " +
	                    " idUs	    VARCHAR(20)    NOT NULL, " +
	                    " PRIMARY KEY(idConfig))";
	            s.executeUpdate(instruccion);
	            s.close();
	            //Creamos una tabla para las configuraciones de usuario
	            s = c.createStatement();
	            instruccion = "CREATE TABLE ConfiguracionCarta " +
	                    "(idConfig	INT    NOT NULL, " +
	                    "numCarta	INT NOT NULL, " +
	                    "nombreImagen		VARCHAR(20) NOT NULL, " +
	                    " FOREIGN KEY (idConfig) REFERENCES ConfiguracionUs(idConfig))";
	            s.executeUpdate(instruccion);
	            
	            //Creamos una tabla para la Partida
	            s = c.createStatement();
	            instruccion = "CREATE TABLE Partida " +
	            		"(ayUsuario	INT    NOT NULL, " +
	                    "idPartida	INT    NOT NULL, " +
	                    "turno	BOOLEAN NOT NULL, " +
	                    "manoMaq	VARCHAR(200)    NOT NULL, " +
	                    "manoUs	VARCHAR(200)    NOT NULL, " +
	                    "calle	VARCHAR(200)    NOT NULL, " +
	                    "bar	VARCHAR(200)    NOT NULL, " +
	                    "mazoMaq	VARCHAR(200)    NOT NULL, " +
	                    "mazoUs	VARCHAR(200)    NOT NULL, " +
	                    "cola		VARCHAR(200) NOT NULL, " +
	                    "idUs	INT    NOT NULL, " +
	                    "fecha	DATETIME    NOT NULL, " +
	                    " PRIMARY KEY(idPartida))";
	            s.executeUpdate(instruccion);
	            
	            //Creamos una tabla para la Partida
	            s = c.createStatement();
	            instruccion = "INSERT INTO Partida (idPartida, Fecha) VALUES(" + "'" + 2 + "'" + "," + "datetime('now')" + ")";

			s.executeUpdate(instruccion);
	            
	            s.close();
	            c.close();
	        } catch (Exception e) {
	            System.err.println(e.getClass().getName() + ": " + e.getMessage());
	            System.exit(0);
	        }
	        System.out.println("Tabla creada");
	    }
	    


		public static ResultSet execSql(String consulta) {
			ResultSet rs = null;
			try {
		            Class.forName("org.sqlite.JDBC");
		            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
		            c.setAutoCommit(false);
		            s = c.createStatement();
		            rs = s.executeQuery(consulta);
			 } catch (Exception e) {
		            System.err.println(e.getClass().getName() + ": " + e.getMessage());
		            System.exit(0);
		        }
			
			return rs;
		}


		public static void sqlUpdate(String strUpdate) {
			 try {
		            Class.forName("org.sqlite.JDBC");
		            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
		            c.setAutoCommit(false);

		            s = c.createStatement();

		            s.executeUpdate(strUpdate);

		            s.close();
		            c.commit();
		            c.close();

		        } catch (Exception e) {
		            System.err.println(e.getClass().getName() + ": " + e.getMessage());
		            System.exit(0);
		        }
		        System.out.println("Actualizado");
			
		}
}