package packModelo;

public class Carta implements Comparable<Carta> {
    private Animal animal;
    private EnumColor color;

    public Carta(Animal animal, EnumColor color) {
        this.animal = animal;
        this.color = color;
    }

    public Animal getAnimal() {
        return this.animal;
    }

    public EnumColor getColor() {
        return this.color;
    }

    public String getEspecie() {
        return this.animal.getEspecie();
    }

    public int getFuerza() {
        return this.animal.getFuerza();
    }

    public void hacerAnimalada() {
        this.animal.hacerAnimalada();
    }

    @Override
    public int compareTo(Carta o) {
        if (o.getAnimal().getFuerza() <= this.animal.getFuerza()) {
            return -1;
        } else {
            if (o.getAnimal().getFuerza() > this.animal.getFuerza()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

	public int getNumero() {
		int numeroEspecie = 0;
		
		 switch (getEspecie()) {
		 case "Camaleon":
             numeroEspecie = 5;
             break;
         case "Canguro":
        	 numeroEspecie = 3;
             break;
         case "Cebra":
        	 numeroEspecie = 7;
             break;
         case "Cocodrilo":
        	 numeroEspecie = 10;
             break;
         case "Foca":
        	 numeroEspecie = 6;
             break;
         case "Hipopotamo":
        	 numeroEspecie = 11;
             break;
         case "Jirafa":
        	 numeroEspecie = 8;
             break;
         case "Leon":
        	 numeroEspecie = 12;
             break;
         case "Loro":
        	 numeroEspecie = 2;
             break;
         case "Mofeta":
        	 numeroEspecie = 1;
             break;
         case "Mono":
        	 numeroEspecie = 4;
             break;
         case "Serpiente":
        	 numeroEspecie = 9;
             break;
     }
		return numeroEspecie;
	}
}
