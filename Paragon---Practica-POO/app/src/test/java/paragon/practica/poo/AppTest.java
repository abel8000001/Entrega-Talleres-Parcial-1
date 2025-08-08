package paragon.practica.poo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import paragon.practica.poo.database.Database;
import paragon.practica.poo.model.Habilidad;
import paragon.practica.poo.model.Item;
import paragon.practica.poo.model.Jugador;
import paragon.practica.poo.model.Personaje;
import paragon.practica.poo.model.clasespersonaje.Guerrero;

public class AppTest {

    @BeforeEach
    public void setUp() {
        // Preparamos una base de datos simulada en memoria

        Jugador jugador1 = new Jugador("user1", "pass1");
        Jugador jugador2 = new Jugador("user2", "pass2");

        // Personajes con una habilidad y un item
        Personaje p1 = new Guerrero("Hero1", jugador1);
        p1.getHabilidades().clear();
        p1.getHabilidades().add(Habilidad.RAYO_ARCANO);
        p1.getItems().clear();
        p1.getItems().add(Item.ESPADA);

        Personaje p2 = new Guerrero("Hero2", jugador2);
        p2.getHabilidades().clear();
        p2.getHabilidades().add(Habilidad.RAYO_ARCANO);
        p2.getItems().clear();
        p2.getItems().add(Item.ESPADA);

        jugador1.getPersonajes().add(p1);
        jugador2.getPersonajes().add(p2);

        Database.getJugadores().add(jugador1);
        Database.getJugadores().add(jugador2);
    }

    @Test
    public void testLoginCorrecto() throws IOException {
        String input = "user1\npass1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Jugador result = App.login(scanner);
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
    }

    @Test
    public void testLoginIncorrecto() throws IOException {
        String input = "user1\nwrongpass\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Jugador result = App.login(scanner);
        assertNull(result);
    }

    @Test
    public void testRegistrarseUsuarioNuevo() {
        String input = "nuevoUser\nnuevoPass\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        App.registrarse(scanner);

        assertTrue(Database.getJugadores().stream()
                .anyMatch(j -> j.getUsername().equals("nuevoUser") && j.getPassword().equals("nuevoPass")));
    }

    @Test
    public void testRegistrarseDatosInvalidos() {
        int cantidadInicial = Database.getJugadores().size();
        String input = "\n\n"; // Usuario y contraseña vacíos
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        App.registrarse(scanner);

        assertEquals(cantidadInicial, Database.getJugadores().size());
    }

    @Test
    public void testSeleccionarHabilidad() {
        Jugador jugador = Database.getJugadores().get(0);
        Personaje personaje = jugador.getPersonajes().get(0);

        String input = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Habilidad habilidad = App.seleccionarHabilidad(personaje, scanner);
        assertEquals(Habilidad.RAYO_ARCANO, habilidad);
    }

    @Test
    public void testSeleccionarItem() {
        Jugador jugador = Database.getJugadores().get(0);
        Personaje personaje = jugador.getPersonajes().get(0);

        String input = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Item item = App.seleccionarItem(personaje, scanner);
        assertEquals(Item.ESPADA, item);
    }

    @Test
    public void testPelear_Rendirse() {
        Jugador jugador1 = Database.getJugadores().get(0);
        Jugador jugador2 = Database.getJugadores().get(1);

        Personaje p1 = jugador1.getPersonajes().get(0);
        Personaje p2 = jugador2.getPersonajes().get(0);

        String input = "4\n"; // Primer turno -> rendirse
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        App.pelear(p1, p2, scanner);

        // Ambos deberían regenerar salud después
        assertEquals(p1.getSaludMaxima(), p1.getPuntosSalud());
        assertEquals(p2.getSaludMaxima(), p2.getPuntosSalud());
    }

    @Test
    public void testPelear_Atacar() {
        Jugador jugador1 = Database.getJugadores().get(0);
        Jugador jugador2 = Database.getJugadores().get(1);

        Personaje p1 = jugador1.getPersonajes().get(0);
        Personaje p2 = jugador2.getPersonajes().get(0);

        // Un turno atacando y luego rendirse para salir del bucle
        String input = "1\n4\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        App.pelear(p1, p2, scanner);

        // p2 debería haber recibido algo de daño antes de regenerar
        assertEquals(p1.getSaludMaxima(), p1.getPuntosSalud());
        assertEquals(p2.getSaludMaxima(), p2.getPuntosSalud());
    }
}
