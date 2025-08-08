package paragon.practica.poo.model.clasespersonaje;

import paragon.practica.poo.model.Jugador;
import paragon.practica.poo.model.Personaje;
import paragon.practica.poo.utils.Utils;

public class Mago extends Personaje {
    
    public Mago(String nombre, Jugador jugador) {
        super(nombre, jugador);
        setPuntosSalud(Utils.randomInRange(60, 120));
        setSaludMaxima(getPuntosSalud());
        setPuntosMana(Utils.randomInRange(50, 100));
        setManaMaxima(getPuntosMana());
        setFuerza(Utils.randomInRange(5, 15));
        setInteligencia(Utils.randomInRange(25, 40));
        setDestreza(Utils.randomInRange(5, 15));
    }
}
