package soundloud.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import soundloud.Model.Cancion.Cancion;
import soundloud.Model.Colas.ColaReproduccion;

class ColaReproduccionTest {
    @Test
    void testAgregarYReproducirCancion() {
        ColaReproduccion cola = new ColaReproduccion();
        Cancion c = new Cancion("Song", "Artist", "3:00", "url");
        cola.agregarCancion(c);
        assertFalse(cola.estaVacia());
        Cancion siguiente = cola.reproducirSiguiente();
        assertEquals(c, siguiente);
        assertTrue(cola.estaVacia());
    }

    @Test
    void testVaciarCola() {
        ColaReproduccion cola = new ColaReproduccion();
        cola.agregarCancion(new Cancion("Song", "Artist", "3:00", "url"));
        cola.vaciarCola();
        assertTrue(cola.estaVacia());
    }
}
