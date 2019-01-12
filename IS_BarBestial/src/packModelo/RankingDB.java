package packModelo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class RankingDB {
    private static RankingDB miRankingDB;
    private Connection c;
    private Statement s;

    private RankingDB() {
        File f = new File("ranking.db");
        if (!f.exists()) {
            this.crearRanking();
            this.crearTablaPuntuaciones();
        }
    }

    public static RankingDB getRankingDB() {
        if (miRankingDB == null) {
            miRankingDB = new RankingDB();
        }
        return miRankingDB;
    }

    private void crearRanking() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ranking.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Base de datos creada");
    }

    private void crearTablaPuntuaciones() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ranking.db");

            s = c.createStatement();
            String instruccion = "CREATE TABLE PUNTUACIONES " +
                    "(NOMBRE	VARCHAR(20) NOT NULL, " +
                    " FECHA	    DATETIME    NOT NULL, " +
                    " PUNTUACION	INT, " +

                    " PRIMARY KEY(NOMBRE, FECHA))";
            s.executeUpdate(instruccion);
            s.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Tabla creada");
    }
    
    public void insertarPuntuacion(String pNombre, int pNCartas, int pFuerza, boolean ayuda) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ranking.db");
            c.setAutoCommit(false);

            s = c.createStatement();
            //TODO comprobar quien ha ganado e incrementar ayudas si se dan las codiciones (+ de 6 cartas)
            int ptsCartas = pNCartas * 10;
            int ptsFuerza = pFuerza * 2;
            int punts = ptsCartas - ptsFuerza;
            if (ayuda) {
            	punts = (punts*9)/10;
            }
            String instruccion = "INSERT INTO PUNTUACIONES (NOMBRE, FECHA, PUNTUACION) " +
                    "VALUES(" + "'" + pNombre + "'" + "," + "datetime('now')" + "," + punts +")";

            s.executeUpdate(instruccion);

            s.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Puntuacion insertada");
    }

    public Vector<Vector<String>> getMejInd() {
        Vector<String> puntuacion;
        Vector<Vector<String>> puntuaciones = new Vector<>();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ranking.db");
            c.setAutoCommit(false);
            s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT fecha, puntuacion FROM PUNTUACIONES WHERE nombre = 'oliver' ORDER BY puntuacion desc;"); //TODO cambiar where usuario = usuario actual


            while (rs.next()) {
                puntuacion = new Vector<>();

               // String nombre = rs.getString("nombre");
                String fecha = rs.getString("fecha");
                String pts = rs.getString("puntuacion");

              //  puntuacion.add(nombre);
                puntuacion.add(fecha);
                puntuacion.add(pts);

                puntuaciones.add(puntuacion);
            }

            rs.close();
            s.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Consulta terminada");
        return puntuaciones;
    }
    
    public Vector<Vector<String>> getMejHoy() {
        Vector<String> puntuacion;
        Vector<Vector<String>> puntuaciones = new Vector<>();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ranking.db");
            c.setAutoCommit(false);
            s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM PUNTUACIONES WHERE DATE(FECHA)= current_date ORDER BY PUNTUACION desc LIMIT 1;");

            while (rs.next()) {
                puntuacion = new Vector<>();

                String nombre = rs.getString("nombre");
                //String fecha = rs.getString("fecha");
                String pts = rs.getString("puntuacion");

                puntuacion.add(nombre);
              //  puntuacion.add(fecha);
                puntuacion.add(pts);

                puntuaciones.add(puntuacion);
            }

            rs.close();
            s.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Consulta terminada");
        return puntuaciones;
    }
    
    public Vector<Vector<String>> getMejSiempre() {
        Vector<String> puntuacion;
        Vector<Vector<String>> puntuaciones = new Vector<>();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ranking.db");
            c.setAutoCommit(false);
            s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM PUNTUACIONES ORDER BY PUNTUACION desc LIMIT 10;");

            while (rs.next()) {
                puntuacion = new Vector<>();

                String nombre = rs.getString("nombre");
                String fecha = rs.getString("fecha");
                String pts = rs.getString("puntuacion");

                puntuacion.add(nombre);
                puntuacion.add(fecha);
                puntuacion.add(pts);

                puntuaciones.add(puntuacion);
            }

            rs.close();
            s.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Consulta terminada");
        return puntuaciones;
    }
    
    public Vector<Vector<String>> getMejMedias() {
        Vector<String> puntuacion;
        Vector<Vector<String>> puntuaciones = new Vector<>();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ranking.db");
            c.setAutoCommit(false);
            s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT nombre, AVG(puntuacion) as Media FROM PUNTUACIONES GROUP BY nombre ORDER BY Media desc LIMIT 10;");
            //TODO CAMBIAR POR : SELECT idUsuario, nombre, AVG(puntuacion) as Media FROM PUNTUACIONES GROUP BY idUsuario ORDER BY PUNTUACION LIMIT 10;
            while (rs.next()) {
                puntuacion = new Vector<>();

                String nombre = rs.getString("nombre");
             //   String fecha = rs.getString("fecha");
                String media = rs.getString("Media");

                puntuacion.add(nombre);
            //    puntuacion.add(fecha);
                puntuacion.add(media);

                puntuaciones.add(puntuacion);
            }

            rs.close();
            s.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Consulta terminada");
        return puntuaciones;
    }
}
   