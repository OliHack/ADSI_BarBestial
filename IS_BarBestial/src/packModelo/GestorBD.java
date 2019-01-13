package packModelo;

import java.io.*;
import java.sql.*;
import java.util.*;

public class GestorBD {
	
	
		private static GestorBD miGestorBD;
	    private Connection c;
	    private Statement s;

	    
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
	                    "numCarta	INT	 NOT NULL, " +
	                    "nombreImagen		VARCHAR(20) NOT NULL, " +
	                    " FOREIGN KEY (idConfig) REFERENCES ConfiguracionUs(idConfig))";
	            s.executeUpdate(instruccion);
	            
	            //Creamos una tabla para la Partida
	            s = c.createStatement();
	            instruccion = "CREATE TABLE Partida " +
	            		"(ayUsuario	INT, " +
	                    "idPartida	INTEGER, " +
	                    "turno	INT, " +
	                    "manoMaq	VARCHAR(200), " +
	                    "manoUs	VARCHAR(200), " +
	                    "calle	VARCHAR(200), " +
	                    "bar	VARCHAR(200), " +
	                    "mazoMaq	VARCHAR(200), " +
	                    "mazoUs	VARCHAR(200), " +
	                    "cola		VARCHAR(200), " +
	                    "idUs	VARCHAR(200), " +
	                    "fecha	DATETIME, " +
	                    " PRIMARY KEY(idPartida))";
	            s.executeUpdate(instruccion);
	            
	            //Creamos una tabla para el usuario
	            s=c.createStatement();
	            instruccion = "CREATE TABLE USUARIO" +
	            		"(idUsuario VARCHAR(200) , " +
	            		"password VARCHAR(200) , " +
	            		"numAyudas INT NOT NULL, " +
	            		" PRIMARY KEY (idUsuario))";
	            s.executeUpdate(instruccion);
	            
	           /* s=c.createStatement();
	            instruccion = "INSERT INTO USUARIO (idUsuario,numAyudas) VALUES ('Unai',4)";
	            s.executeUpdate(instruccion);*/
           		
	            		
	            		
	            
	            //Creamos una tabla para la Partida
	            /*s = c.createStatement();
	            instruccion = "INSERT INTO Partida (idPartida, fecha) VALUES (2," + "datetime('now')" + ")";
	           
	            s.executeUpdate(instruccion);*/
	            
	            s.close();
	            c.close();
	        } catch (Exception e) {
	            System.err.println(e.getClass().getName() + ": " + e.getMessage());
	            System.exit(0);
	        }
	        System.out.println("Tabla creada");
	    }
	    
	    public Vector<Vector<String>> obtenerPartidas(String nombre) {
	        Vector<String> info;
	        Vector<Vector<String>> partidas = new Vector<>();

	        try {
	            Class.forName("org.sqlite.JDBC");
	            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
	            c.setAutoCommit(false);
	            s = c.createStatement();
	            
	            ResultSet rs = s.executeQuery("SELECT idPartida, fecha FROM Partida WHERE idUs=" + "'" + nombre + "'" + ";");

	            
	            while (rs.next()) {
	                info = new Vector<>();

	                String id = rs.getString("idPartida");
	                String fecha = rs.getString("fecha");
	                String idR = "Partida Guardada " + id;
	                		
	                info.add(idR);
	                info.add(fecha);

	                partidas.add(info);
	            }

	            rs.close();
	            s.close();
	            c.close();

	        } catch (Exception e) {
	            System.err.println(e.getClass().getName() + ": " + e.getMessage());
	            System.exit(0);
	        }
	        System.out.println("Consulta terminada");
	        return partidas;
	    }
	    
	    public Vector<String> infoPartidas(){
	    	Vector<String> info = new Vector<>();
	    	 try {
		            Class.forName("org.sqlite.JDBC");
		            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
		            c.setAutoCommit(false);
		            s = c.createStatement();
		            
		            ResultSet rs = s.executeQuery("SELECT ayUsuario,turno,manoMaq,manoUs,calle,bar,mazoMaq,mazoUs,cola,idUs FROM Partida;");

		            
		            while (rs.next()) {
		                info = new Vector<>();

		                String ayUs = rs.getString("ayUsuario");
		                String turno = rs.getString("turno");
		                String manoMaq = rs.getString("manoMaq");
		                String manoUs = rs.getString("manoUs");
		                String calle = rs.getString("calle");
		                String bar = rs.getString("bar");
		                String mazoMaq = rs.getString("mazoMaq");
		                String mazoUs = rs.getString("mazoUs");
		                String cola = rs.getString("cola");
		                String idUs = rs.getString("idUs");
		                
		                info.add(ayUs);
		                info.add(turno);
		                info.add(manoMaq);
		                info.add(manoUs);
		                info.add(calle);
		                info.add(bar);
		                info.add(mazoMaq);
		                info.add(mazoUs);
		                info.add(cola);
		                info.add(idUs);
		            }

		            rs.close();
		            s.close();
		            c.close();

		        } catch (Exception e) {
		            System.err.println(e.getClass().getName() + ": " + e.getMessage());
		            System.exit(0);
		        }
	    	
	    	return info;
	    }
	    
	    public void obtenerInfoyGuardarPartida() {

	    	Partida part = Partida.getMiPartida();
	    	int turnoAct = part.getTurno();	    	
	    	
	    	//Info Bar
	    	Bar b = Bar.getMiBar();
	    	ListaCartas elBar = b.getLista();
	    	String bar = elBar.convertirListaStringColor();
	    	
	    	//Info calle
	    	EsLoQueHay c = EsLoQueHay.getMiEsLoQueHay();
	    	ListaCartas laCalle = c.getCalle();
	    	String calle = laCalle.convertirListaStringColor();
	    	
	    	//Info cola (ordenado)
	    	Tablero t = Tablero.getMiTablero();
	    	ListaCartas laCola = t.getCola();
	    	String cola = laCola.convertirListaStringColor();	    	
	    	
	    	ArrayList<Jugador> lisJug = part.obtenerJugadores();
	    	
	    	Jugador jugReal = lisJug.get(0);
	    	Jugador maquina = lisJug.get(1);
	    	
	    	//Info del jugador
	    	String nombre = jugReal.getNombre();
	    	int ayUs = jugReal.getNumAyudas();
	    	
	    	ListaCartas mazoJugador = jugReal.getMazo();
	    	String mazoJug = mazoJugador.convertirListaString();
	    	
	    	ListaCartas manoJugador = jugReal.getMano();
	    	String manoJug = manoJugador.convertirListaString();
	    	
	    	//Info Maquina
	    	ListaCartas mazoMaquina = maquina.getMazo();
	    	String mazoMaq = mazoMaquina.convertirListaString();
	    	
	    	ListaCartas manoMaquina = maquina.getMano();
	    	String manoMaq = manoMaquina.convertirListaString();
	    	
	    	sqlUpdate("INSERT INTO Partida (ayUsuario,turno,manoMaq,manoUs,calle,bar,mazoMaq,mazoUs,cola,idUs,fecha) VALUES(" + ayUs + "," + turnoAct + "," + "'" + manoMaq + "'" + "," + "'" + manoJug + "'" + "," + "'" + calle + "'" + "," + "'" + bar + "'" + "," + "'" + mazoMaq + "'" + "," + "'" + mazoJug + "'" + "," + "'" + cola + "'" + "," + "'" + nombre + "'" + "," + "datetime('now')" + ");");
	    }
	    
	    public void insertarPartidaPrueba() {
	    	sqlUpdate("INSERT INTO Partida (fecha) VALUES (" + "datetime('now')" + ")");
	    }
	    
	    public void actualizarAyudas(int ayudaResta, String user) {
	    	sqlUpdate("UPDATE Usuario SET numAyudas = " + ayudaResta + " WHERE idUsuario = '"+ user + "'");
	    }
	    
	    public int cargarAyudas(String user) {
	    		int res=0;
		    	 try {
			            Class.forName("org.sqlite.JDBC");
			            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
			            c.setAutoCommit(false);
			            s = c.createStatement();
			            
			            ResultSet rs = s.executeQuery("SELECT numAyudas FROM Usuario WHERE idUsuario = '"+ user + "'");

			            
			            while (rs.next()) {
			               res= rs.getInt("numAyudas");
			            }

			            rs.close();
			            s.close();
			            c.close();

			        } catch (Exception e) {
			            System.err.println(e.getClass().getName() + ": " + e.getMessage());
			            System.exit(0);
			        }
		    	
		    	return res;
		}
	    
	    public int contarPartidas() {
	    	int num=0;
	    	try {
	            Class.forName("org.sqlite.JDBC");
	            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
	            c.setAutoCommit(false);
	            s = c.createStatement();
	            
	            ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Partida;");
	            
	            
	            while (rs.next()) {
	                num = rs.getInt("COUNT(*)");
	            }

	            rs.close();
	            s.close();
	            c.close();

	        } catch (Exception e) {
	            System.err.println(e.getClass().getName() + ": " + e.getMessage());
	            System.exit(0);
	        }
	        System.out.println("Consulta terminada contar");
	        return num;
	    }
	    
		public ResultSet execSql(String consulta) {
			ResultSet rs = null;
			try {
		            Class.forName("org.sqlite.JDBC");
		            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
		            c.setAutoCommit(false);
		            s = c.createStatement();
		            rs = s.executeQuery(consulta);
		            s.close();
		            c.close();
		            
			 } catch (Exception e) {
		            System.err.println(e.getClass().getName() + ": " + e.getMessage());
		            System.exit(0);
		        }
			
			return rs;
		}


		public void sqlUpdate(String strUpdate) {
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
		
		public void introducirUsuario(String user, String pass) {
			sqlUpdate("INSERT INTO usuario (idUsuario, password, numAyudas) VALUES ('" + user + "', '" + pass + "', 4)");
		}

		public boolean comprobarLogin(String user, String pass) {
			boolean resultado = false;
	    	 try {
		            Class.forName("org.sqlite.JDBC");
		            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
		            c.setAutoCommit(false);
		            s = c.createStatement();
		            
		            ResultSet rs = s.executeQuery("SELECT * FROM Usuario WHERE idUsuario = '" + user + "' AND password = '" + pass + "'");

		            String usuario = "";
		            String contrasena = "";
		            while (rs.next()) {

		                usuario = rs.getString("idUsuario");
		                contrasena = rs.getString("password");
		            }
		            
		            if ((usuario.equals(user)) && (contrasena.equals(pass))) {
		            	resultado = true;
		            }
		            
		            
		            rs.close();
		            s.close();
		            c.close();

		        } catch (Exception e) {
		            System.err.println(e.getClass().getName() + ": " + e.getMessage());
		            System.exit(0);
		        }
	    	
	    	return resultado;
		}


		public String recuperarContrasena(String user) {
			String pass = null;
			try {
	            Class.forName("org.sqlite.JDBC");
	            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
	            c.setAutoCommit(false);
	            s = c.createStatement();
	            
	            ResultSet rs = s.executeQuery("SELECT password FROM Usuario where idUsuario = '" + user + "'");

	            String contrasena = "";
	            while (rs.next()) {
	                contrasena = rs.getString("password");
	            }
	            
	            pass = contrasena;
	            
	            
	            rs.close();
	            s.close();
	            c.close();

	        } catch (Exception e) {
	            System.err.println(e.getClass().getName() + ": " + e.getMessage());
	            System.exit(0);
	        }
    	
    	return pass;
	}

		public void cambiarContrasena(String usuarioAct, String pass) {
			sqlUpdate("UPDATE Usuario SET password= '" + pass + "' WHERE idUsuario = '" + usuarioAct + "'");
		}
}