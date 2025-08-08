package soundloud.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import soundloud.Model.Cancion.Cancion;

class CancionTest {
    @Test
    void testConstructorAndGetters() {
        Cancion c = new Cancion("Song", "Artist", "3:00", "http://youtube.com");
        assertEquals("Song", c.getTitulo());
        assertEquals("Artist", c.getArtista());
        assertEquals("3:00", c.getDuracion());
        assertEquals("http://youtube.com", c.getUrlYouTube());
    }

    @Test
    void testToString() {
        Cancion c = new Cancion("Song", "Artist", "3:00", "http://youtube.com");
        assertEquals("Song - Artist (3:00 min)", c.toString());
    }

    @Test
    void testNullParameters() {
        assertThrows(IllegalArgumentException.class, () -> new Cancion(null, "Artist", "3:00", "url"));
        assertThrows(IllegalArgumentException.class, () -> new Cancion("Song", null, "3:00", "url"));
        assertThrows(IllegalArgumentException.class, () -> new Cancion("Song", "Artist", null, "url"));
        assertThrows(IllegalArgumentException.class, () -> new Cancion("Song", "Artist", "3:00", null));
    }

    @Test
    void testEmptyParameters() {
        assertThrows(IllegalArgumentException.class, () -> new Cancion("", "Artist", "3:00", "url"));
        assertThrows(IllegalArgumentException.class, () -> new Cancion("Song", "", "3:00", "url"));
        assertThrows(IllegalArgumentException.class, () -> new Cancion("Song", "Artist", "", "url"));
        assertThrows(IllegalArgumentException.class, () -> new Cancion("Song", "Artist", "3:00", ""));
    }
}
