package paragon.practica.poo.model.clasespersonaje;

import paragon.practica.poo.model.Jugador;
import paragon.practica.poo.model.Personaje;
import paragon.practica.poo.utils.Utils;

public class Picaro extends Personaje {
    
    public Picaro(String nombre, Jugador jugador) {
        super(nombre, jugador);
        setPuntosSalud(Utils.randomInRange(80, 120));
        setSaludMaxima(getPuntosSalud());
        setPuntosMana(Utils.randomInRange(40, 70));
        setManaMaxima(getPuntosMana());
        setFuerza(Utils.randomInRange(5, 15));
        setInteligencia(Utils.randomInRange(5, 20));
        setDestreza(Utils.randomInRange(25, 40));
    }
}
