package soundloud.Model.Colas;

import java.util.LinkedList;
import java.util.Queue;

import soundloud.Model.Cancion.Cancion;

public class ColaReproduccion{
     private Queue<Cancion> cola;

    public ColaReproduccion() {
        cola = new LinkedList<>();
    }

    public void agregarCancion(Cancion cancion) {
        cola.add(cancion);
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public Cancion reproducirSiguiente() {
        return cola.poll(); // Devuelve y elimina la primera
    }

    public void vaciarCola() {
        cola.clear();
    }

}
