package soundloud.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import soundloud.Model.Cancion.Cancion;
import soundloud.Model.ListaReproduccion.ListaReproduccion;

class ListaReproduccionTest {
    @Test
    void testConstructorAndGetters() {
        ListaReproduccion lista = new ListaReproduccion("Mi lista", "desc");
        assertEquals("Mi lista", lista.getNombre());
        assertEquals("desc", lista.getDescripcion());
        assertTrue(lista.getCanciones().isEmpty());
    }

    @Test
    void testAgregarCancion() {
        ListaReproduccion lista = new ListaReproduccion("Mi lista", "desc");
        Cancion c = new Cancion("Song", "Artist", "3:00", "url");
        lista.agregarCancion(c);
        assertEquals(1, lista.getCanciones().size());
        assertEquals(c, lista.getCanciones().get(0));
    }

    @Test
    void testParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new ListaReproduccion(null, "desc"));
        assertThrows(IllegalArgumentException.class, () -> new ListaReproduccion("", "desc"));
        assertThrows(IllegalArgumentException.class, () -> new ListaReproduccion("nombre", null));
        assertThrows(IllegalArgumentException.class, () -> new ListaReproduccion("nombre", ""));
    }
}
