package paragon.practica.poo.model;

public enum Habilidad {
    RAYO_ARCANO("Rayo Arcano", "Ataque", 15, 25),
    EXPLOSION_DE_FUEGO("Explosión de Fuego", "Ataque", 20, 35),
    ESCUDO_MISTICO("Escudo Místico", "Curacion", 10, 0),
    TORMENTA_DE_HIELO("Tormenta de Hielo", "Ataque", 18, 28),
    CURACION_PROFUNDA("Curación Profunda", "Curación", 25, 30);

    private final String nombre;
    private final String tipo;
    private final int costoMana;
    private final int puntosDanio;

    Habilidad(String nombre, String tipo, int costoMana, int puntosDanio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.costoMana = costoMana;
        this.puntosDanio = puntosDanio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getCostoMana() {
        return costoMana;
    }

    public int getPuntosDanio() {
        return puntosDanio;
    }
}
