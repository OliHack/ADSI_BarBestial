package packModelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

public abstract class Jugador extends Observable {
    private String nombre;
    private EnumColor colorJugador;    
    private ListaCartas mazo;
    private int numAyudas;
    protected ListaCartas mano;
    protected Carta cartaElegidaMano;
    protected int saltosElegidos;
    protected String especieElegidaCola;    

    public Jugador(String pNombre, EnumColor pColorJugador, int pNumAyudas) {
        this.nombre = pNombre;
        this.colorJugador = pColorJugador;
        this.mano = new ListaCartas();
        this.mazo = new ListaCartas();
        this.numAyudas =4;
    }

    public void robarCarta() {
    	Carta carta = this.mazo.obtenerCartaAleatoria();
    	if (carta != null) {
        	this.anadirCartaALaMano(carta);
        	this.mazo.sacarCarta(carta);
    	}
    }

    public void repartirCartas() {
    	AnimalFactory af = AnimalFactory.getMiAnimalFactory();
    	
        String[] listaAnimales = new String[]{
                "Camaleon", "Canguro", "Cebra", "Cocodrilo", "Foca", "Hipopotamo",
                "Jirafa", "Leon", "Loro", "Mofeta", "Mono", "Serpiente"
        };

        int indice = 0;
        Animal[] animales = new Animal[12];
        for (String s: listaAnimales) {
            Animal animal = af.crearAnimal(s);
            animales[indice] = animal;
            indice++;
        }
       
        int i = 0;
        int aux = 0;
        int maxIter = 10;
        while (i < maxIter) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 12);
            Animal animalAux = animales[aux];
            animales[aux] = animales[randomNum];
            animales[randomNum] = animalAux;
            i++;
        }

        for (int j = 0; j < animales.length; j++) {
            this.anadirCartaAlMazo(new Carta(animales[j], this.colorJugador));
        }
        for (int c = 0; c < 4; c++){
        	this.robarCarta();
        }
    }  
    
    public boolean tieneCartas() {
    	return (this.hayCartasEnMano());
    }
    
    public int obtenerNumeroDeCartasEnMano() {
        return this.mano.obtenerNumeroDeCartas();
    }
    
    public int obtenerNumeroDeCartasEnMazo() {
    	return this.mazo.obtenerNumeroDeCartas();
    }
    
    public String getNombre(){
        return this.nombre;
    }

    public EnumColor getColorJugador(){
        return this.colorJugador;
    }
    
    public ListaCartas getManoJugador(){
    	return this.mano;
    }
    
    public ListaCartas getMazoJugador(){
    	return this.mazo;
    }
    
    public String obtenerEspeciesDeAnimalesEnMano(){
        return this.mano.obtenerEspeciesDeAnimales();
    }

    public void notificar(String pInformacion) {
    	super.setChanged();
    	super.notifyObservers(pInformacion);
    }

    public void anadirObservador(Observer pObservador) {
    		super.addObserver(pObservador);
    }
    
    public String obtenerInformacionCartas() {
    	if (this.mazo.vacia()) {
    		return this.obtenerInformacionCartasMano() + "_mazovacio";
    	}
    	else {
    		return this.obtenerInformacionCartasMano() + "_mazonovacio";
    	}
    }
    
    public String obtenerEspecieElegidaCola() {
    	return this.especieElegidaCola;
    }
    
    public int obtenerSaltosCanguro() {
    	return this.saltosElegidos;
    }
    
    public void jugarTurno() {
		this.echarCarta(cartaElegidaMano); 
		this.robarCarta();
    }  

    public abstract void elegirCartaMano(int pPos);
    
    public abstract void elegirEspecieCola();

    public abstract void elegirSaltosCanguro();
    
    public abstract String obtenerInformacionCartasMano();
    
    protected void echarCarta(Carta pCarta) {
    	Tablero tablero = Tablero.getMiTablero();
        tablero.anadirALaCola(pCarta);
        this.sacarCartaDeLaMano(pCarta);
    }
    
    protected boolean hayCartasEnMazo() {
    	return !this.mazo.vacia();
    }
    
    protected Carta obtenerCartaManoEnPosicion(int pPosCarta) {
    	return this.mano.obtenerCartaEnPosicion(pPosCarta);
    }
    
    private void anadirCartaALaMano(Carta pCarta) {
        this.mano.anadirCarta(pCarta);
        this.notificar(this.obtenerInformacionCartas());
    }

    private void sacarCartaDeLaMano(Carta pCarta) {
        this.mano.sacarCarta(pCarta);
        this.notificar(this.obtenerInformacionCartas());
    }
    
    private void anadirCartaAlMazo(Carta pCarta) {
    	this.mazo.anadirCarta(pCarta);
    }
    
    private boolean hayCartasEnMano() {
    	return !this.mano.vacia();
    }
    
    
    public boolean ayudasMaxPartida(){
    		if (this.numAyudas == 0){
    			return true;
    		}else{
    			return false;
    			}

    }
    
    public int getAyudas(){
    	return this.numAyudas;
    }
      
    public void restarAyuda() {
    	this.numAyudas= this.numAyudas -1;
    	//String sql = String.format("UPDATE Usuario SET numAyudas = numAyudas -1 WHERE idUsuario= %s ; ", this.nombre);
    	//GestorBD.sqlUpdate(sql);
    	//int ayudaResta = this.numAyudas;
    	//GestorBD.sqlUpdate("UPDATE Usuario SET numAyudas = " + ayudaResta + " WHERE idUsuario= 'Unai';");
    }
    
    public void cargarAyuda() throws SQLException{
    	//String sql = String.format("SELECT numAyudas FROM Usuario WHERE idUsuario = %s; ", this.nombre);
    	//ResultSet rs =GestorBD.execSql(sql);
    	ResultSet rs = GestorBD.execSql("SELECT numAyudas FROM Usuario WHERE idUsuario = 'Unai';");
    	this.numAyudas= rs.getInt("numAyudas");
    	rs.close();	
    }
    
    public void usarAyuda() {
    	Bar bar= Bar.getMiBar();
    	Carta aux = null;
    	Tablero tablero = Tablero.getMiTablero();
    	Partida partida= Partida.getMiPartida();
    	if( (partida.obtenerJugadorReal().getAyudas()==0)){
	        JOptionPane.showMessageDialog(null, "No tienes ninguna ayuda.");
	        //partida.obtenerJugadorReal().restarAyuda();
    	}
    	if ((partida.obtenerJugadorReal().getAyudas() !=0)){
    		bar.getLista().imprimirCartasColor(EnumColor.VERDE);
    		
    		if( bar.getLista().obtenerNumeroDeCartasColor(EnumColor.VERDE) ==0) {
    			System.out.println("No hay cartas");
    			JOptionPane.showMessageDialog(null, "No hay ninguna carta rival.");
    			partida.obtenerJugadorReal().restarAyuda();    			
    			System.out.println(partida.obtenerJugadorReal().getAyudas());
    		}
    		else{
    			aux = bar.getLista().obtenerPrimeraCartaRival();
    			System.out.println("Tengo la carta del rival y es: "+ aux.getAnimal().getEspecie());
    			tablero.anadirALaCola(aux);
    			partida.obtenerJugadorReal().restarAyuda();
    			System.out.println(partida.obtenerJugadorReal().getAyudas());
    			System.out.println("He añadido la carta");
    			tablero.hacerUltimaAnimalada();
    			tablero.hacerAnimaladasRecurrentes();
    			tablero.revisarCola();

    			if (partida.comprobarFinalizacion()) {
    				partida.finalizar();
    			}
    			/*else {
    				partida.avanzarTurno();
    			}*/
    		} 
    	}
    }
}
