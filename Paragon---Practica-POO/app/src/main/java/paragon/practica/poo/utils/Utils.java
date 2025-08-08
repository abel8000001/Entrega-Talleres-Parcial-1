package paragon.practica.poo.utils;

public class Utils {
    // Metodo para hacer mas facil la generacion de numeros aleatorios dentro de un rango
    public static int randomInRange(int min, int max) {
        return min + (int)(Math.random() * (max - min + 1));
    }
}
