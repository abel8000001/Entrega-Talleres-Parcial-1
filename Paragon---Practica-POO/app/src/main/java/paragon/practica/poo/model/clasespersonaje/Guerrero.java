package paragon.practica.poo.model.clasespersonaje;

import paragon.practica.poo.model.Jugador;
import paragon.practica.poo.model.Personaje;
import paragon.practica.poo.utils.Utils;

public class Guerrero extends Personaje{
    
    public Guerrero(String nombre, Jugador jugador) {
        super(nombre, jugador);
        setPuntosSalud(Utils.randomInRange(100, 170));
        setSaludMaxima(getPuntosSalud());
        setPuntosMana(Utils.randomInRange(30, 50));
        setManaMaxima(getPuntosMana());
        setFuerza(Utils.randomInRange(25, 40));
        setInteligencia(Utils.randomInRange(5, 15));
        setDestreza(Utils.randomInRange(5, 15));
    }
}
