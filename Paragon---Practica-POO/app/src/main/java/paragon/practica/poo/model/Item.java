package paragon.practica.poo.model;

public enum Item {
    ESPADA("Espada", 10, 0, 0, "+10 Fuerza"),
    AMULETO_SABIDURIA("Amuleto de Sabiduria", 0, 5, 0, "+5 Inteligencia"),
    ARMADURA_LIGERA("Armadura Ligera", 0, 0, 5, "+5 Destreza");

    private final String nombre;
    private final int bonusFuerza;
    private final int bonusInteligencia;
    private final int bonusDestreza;
    private final String descripcion;

    Item(String nombre, int bonusFuerza, int bonusInteligencia, int bonusDestreza, String descripcion) {
        this.nombre = nombre;
        this.bonusFuerza = bonusFuerza;
        this.bonusInteligencia = bonusInteligencia;
        this.bonusDestreza = bonusDestreza;
        this.descripcion = descripcion;
    }

    public String getNombre() { return nombre; }
    public int getBonusFuerza() { return bonusFuerza; }
    public int getBonusInteligencia() { return bonusInteligencia; }
    public int getBonusDestreza() { return bonusDestreza; }
    public String getDescripcion() { return descripcion; }
}