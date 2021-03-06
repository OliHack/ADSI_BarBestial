package packModelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

public class Partida extends Observable {
    private static Partida miPartida;

    /* El turno se representa como un numero
     * que indica la posicion de la lista de jugadores
     * que jugara cada turno. Incrementara y volvera�
     * a 0 cuando todos hayan jugado. */
    private int turnoActual;
    private ArrayList<Jugador> listaJugadores;
    private boolean usadoAyuda=false;
    

    private Partida() {
        this.listaJugadores = new ArrayList<Jugador>();
    }

    public static Partida getMiPartida() {
        if (miPartida == null) {
            miPartida = new Partida();
        }
        return miPartida;
    }

    public int getTurno() {
    	return turnoActual;
    }
    
    public void setTurno(int tur) {
    	turnoActual = tur;
    }
    
    public void inicializarPartida(String pNombreJugador) {
        Tablero tablero = Tablero.getMiTablero();
        tablero.vaciar();
        Bar bar = Bar.getMiBar();
        bar.vaciar();

        EsLoQueHay elqh = EsLoQueHay.getMiEsLoQueHay();
        elqh.vaciar();

        this.listaJugadores.add(new JugadorReal(pNombreJugador, EnumColor.AZUL,0));
        this.listaJugadores.add(new Maquina("Maquina", EnumColor.VERDE,0));

        this.repartirCartas();
        this.turnoActual = 0;
    }
    
    public void anadirJugador(Jugador jug) {
    	listaJugadores.add(jug); 
    }
    
    public void jugarTurno() {
        Tablero tablero = Tablero.getMiTablero();
        Jugador jugador;
        jugador = this.obtenerJugadorTurnoActual();
        jugador.jugarTurno();
        tablero.hacerUltimaAnimalada();
        tablero.hacerAnimaladasRecurrentes();
        tablero.revisarCola();

        if (this.comprobarFinalizacion()) {
            this.finalizar();
        } else {
            this.avanzarTurno();
        }

    }

    public ArrayList<Jugador> obtenerJugadores() {
        return this.listaJugadores;
    }

    public Jugador obtenerJugadorTurnoActual() {
        return this.listaJugadores.get(turnoActual);
    }

    public Jugador obtenerJugadorReal(){
    	int i=0;
    	Jugador aux=null;
    	while (i< this.listaJugadores.size()){
    		if(this.listaJugadores.get(i).getColorJugador().equals(EnumColor.AZUL)){
    			aux= this.listaJugadores.get(i);
    		}
    		i++;
    	}
    	return aux;
    }
    	
    private void repartirCartas() {
        Iterator<Jugador> iterator = this.listaJugadores.iterator();
        Jugador jugador;

        while (iterator.hasNext()) {
            jugador = iterator.next();
            jugador.repartirCartas();
        }
    }

    public void avanzarTurno() {
        this.turnoActual++;

        if (this.turnoActual == this.listaJugadores.size()) {
            turnoActual = 0;
        }
    }

    public void finalizar() {
        String infoGanador = this.obtenerInformacionGanador();
        /* Guardar ganador en la base de datos */
        this.anadirGanadorDatabase(infoGanador);

        /* Notificar a la interfaz */
        this.notificar("fin-" + infoGanador);
    }

    public boolean comprobarFinalizacion() {
        Iterator<Jugador> it = this.listaJugadores.iterator();
        Jugador j;
        boolean quedanCartas = false;

        while (it.hasNext() && !quedanCartas) {
            j = it.next();
            if (j.tieneCartas()) {
                quedanCartas = true;
            }
        }
        return !quedanCartas;
    }

    private String obtenerInformacionGanador() {
        Bar bar = Bar.getMiBar();
        EnumColor color = bar.calcularGanador();
        String infoGanador = null;
        if (color == null) {
            infoGanador = "Empate";
        } else {
            boolean ganadorEncontrado = false;
            Iterator<Jugador> it = this.listaJugadores.iterator();
            Jugador jugador = null;

            while (it.hasNext() && !ganadorEncontrado) {
                jugador = it.next();
                if (jugador.getColorJugador().equals(color)) {
                    ganadorEncontrado = true;
                    infoGanador = jugador.getNombre();
                }
            }
            String numeroDeCartasGanador = Integer.toString(this.obtenerNumeroDeCartasColorEnBar(color));
            String fuerzaGanador = Integer.toString(this.obtenerFuerzaColorEnBar(color));
            infoGanador = infoGanador + " " + numeroDeCartasGanador + " " + fuerzaGanador;
        }
        return infoGanador;
    }

    private int obtenerNumeroDeCartasColorEnBar(EnumColor pColor) {
        return Bar.getMiBar().obtenerNumeroDeCartasColor(pColor);
    }

    private int obtenerFuerzaColorEnBar(EnumColor pColor) {
        return Bar.getMiBar().obtenerFuerzaColor(pColor);
    }

    private void anadirGanadorDatabase(String pInformacionGanador) {
        GestorRanking r = GestorRanking.getRankingDB();
        String nombre = pInformacionGanador.split(" ")[0];
        int nCartas = Integer.parseInt(pInformacionGanador.split(" ")[1]);
        int fuerza = Integer.parseInt(pInformacionGanador.split(" ")[2]);

        r.insertarPuntuacion(nombre, nCartas, fuerza, this.usadoAyuda);
        //A�adir parametro this.usadoAyuda
    }
    
    public void ayudaUsada(){
    	this.usadoAyuda=true;
    	
    }
    
    public void addJugador( Jugador jug){
    	this.listaJugadores.add(jug);
    }
    
    private void notificar(String pInformacion) {
        super.setChanged();
        super.notifyObservers(pInformacion);
    }
    
}
