package paragon.practica.poo.model;

import java.util.ArrayList;
import java.util.Random;

public abstract class Personaje {

    private int id;
    private String tipo;
    private String nombre;
    private int nivel;
    private int puntosSalud;
    private int saludMaxima;
    private int puntosMana;
    private int manaMaxima;
    private int fuerza;
    private int inteligencia;
    private int destreza;
    private int experiencia;
    private int experienciaSiguienteNivel;
    private ArrayList<Habilidad> habilidades;
    private ArrayList<Item> items;

    private transient Random rand = new Random();

    public Personaje(String nombre, Jugador jugador) {
        id++;
        tipo = this.getClass().getSimpleName();
        this.nombre = nombre;
        nivel = 1;
        experiencia = 0;
        experienciaSiguienteNivel = 100;

        habilidades = new ArrayList<>();
        items = new ArrayList<>();

        Habilidad[] todasHabilidades = Habilidad.values();
        while (habilidades.size() < 2) {
            Habilidad h = todasHabilidades[rand.nextInt(todasHabilidades.length)];
            if (!habilidades.contains(h)) {
                habilidades.add(h);
            }
        }

        Item[] todosItems = Item.values();
        while (items.size() < 2) {
            Item item = todosItems[rand.nextInt(todosItems.length)];
            if (!items.contains(item)) {
                items.add(item);
            }
        }

        jugador.addPersonaje(this);
    }

    // Getters
    public String getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public int getPuntosSalud() {
        return puntosSalud;
    }

    public int getSaludMaxima() {
        return saludMaxima;
    }

    public int getPuntosMana() {
        return puntosMana;
    }

    public int getManaMaxima() {
        return manaMaxima;
    }

    public int getFuerza() {
        return fuerza;
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public int getDestreza() {
        return destreza;
    }

    public ArrayList<Habilidad> getHabilidades() {
        return habilidades;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    // Setters
    public void setNombre(String nombre) {
        if (nombre == null || nombre.equals("")) {
            throw new IllegalArgumentException("Debe introducir un nombre");
        }
        this.nombre = nombre.trim();
    }

    public void setPuntosSalud(int puntosSalud) {
        this.puntosSalud = puntosSalud;
    }

    public void setSaludMaxima(int saludMaxima) {
        this.saludMaxima = saludMaxima;
    }

    public void setPuntosMana(int puntosMana) {
        this.puntosMana = puntosMana;
    }

    public void setManaMaxima(int manaMaxima) {
        this.manaMaxima = manaMaxima;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n"
                + "Clase: " + getClass().getSimpleName() + "\n"
                + "Nivel: " + nivel + "\n"
                + "Salud: " + puntosSalud + "/" + saludMaxima + "\n"
                + "Mana: " + puntosMana + "/" + manaMaxima + "\n"
                + "Fuerza: " + fuerza + "\n"
                + "Inteligencia: " + inteligencia + "\n"
                + "Destreza: " + destreza + "\n"
                + "Habilidades: " + habilidades.stream()
                        .map(Habilidad::getNombre)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("Ninguna")
                + "\n"
                + "Items: " + items.stream()
                        .map(Item::getNombre)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("Ninguno")
                + "\n";
    }

    // Acciones
    public void atacar(Personaje objetivo) {
        objetivo.setPuntosSalud((objetivo.getPuntosSalud() - (10 + (this.fuerza) / 3)) - (objetivo.getDestreza() / 2));

        if (objetivo.getPuntosSalud() < 0) {
            objetivo.setPuntosSalud(0);
        }
    }

    public boolean usarHabilidad(Habilidad habilidad, Personaje objetivo) {
        if (this.getPuntosMana() < habilidad.getCostoMana()) {
            return false;
        }

        if (habilidad.getTipo() == "Ataque") {
            objetivo.setPuntosSalud(
                    ((objetivo.getPuntosSalud() - habilidad.getPuntosDanio()) + (this.getInteligencia() / 2))
                            - (objetivo.getDestreza() / 4));
        } else if (habilidad.getTipo() == "Curación") {
            this.setPuntosSalud((this.getPuntosSalud() + habilidad.getPuntosDanio()) + (this.getInteligencia() / 2));
        }

        this.setPuntosMana(this.getPuntosMana() - habilidad.getCostoMana());

        if (objetivo.getPuntosSalud() < 0) {
            objetivo.setPuntosSalud(0);
        }

        return true;
    }

    public void usarItem(Item item, Personaje objetivo) {
        objetivo.setFuerza(item.getBonusFuerza());
        objetivo.setInteligencia(item.getBonusInteligencia());
        objetivo.setDestreza(item.getBonusDestreza());
    }

    public void regenerar() {
        setPuntosSalud(getSaludMaxima());
        setPuntosMana(getManaMaxima());
    }

    public void ganarExperiencia(int cantidad) {
        experiencia += cantidad;
        System.out.println(nombre + " ha ganado " + cantidad + " puntos de experiencia. EXP actual: " + experiencia
                + "/" + experienciaSiguienteNivel);

        while (experiencia >= experienciaSiguienteNivel) {
            experiencia -= experienciaSiguienteNivel;
            subirNivel();
        }
    }

    private void subirNivel() {
        nivel++;
        experienciaSiguienteNivel += 50;

        saludMaxima += 10;
        puntosSalud = saludMaxima;
        manaMaxima += 5;
        puntosMana = manaMaxima;
        fuerza += 2;
        inteligencia += 2;
        destreza += 1;

        System.out.println("¡" + nombre + " ha subido al nivel " + nivel + "!");
        System.out.println("Nuevas estadísticas: Salud " + saludMaxima + ", Mana " + manaMaxima +
                ", Fuerza " + fuerza + ", Inteligencia " + inteligencia + ", Destreza " + destreza);
    }

}
