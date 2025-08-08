package paragon.practica.poo.model;

import java.util.ArrayList;

public class Jugador {
    private String username;
    private String password;
    private ArrayList<Personaje> personajes;

    public Jugador(String username, String password) {
        this.username = username;
        this.password = password;
        this.personajes = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    public void addPersonaje(Personaje personaje) {
        personajes.add(personaje);
    }

    public void removePersonaje(Personaje personaje) {
        personajes.remove(personaje);
    }
}
