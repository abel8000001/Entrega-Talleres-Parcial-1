package soundloud.Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import soundloud.Model.Perfil.Perfil;
import soundloud.Model.ListaReproduccion.ListaReproduccion;
import java.util.UUID;

class PerfilTest {
    @Test
    void testConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        Perfil perfil = new Perfil(id, "Miguel", "1234", "desc", "rock");
        assertEquals(id, perfil.getId());
        assertEquals("Miguel", perfil.getNombre());
        assertEquals("desc", perfil.getDescripcion());
        assertEquals("rock", perfil.getGeneroFavorito());
        assertEquals("1234", perfil.getContrasena());
        assertTrue(perfil.getListasReproduccion().isEmpty());
    }

    @Test
    void testAgregarLista() {
        Perfil perfil = new Perfil(UUID.randomUUID(), "Miguel", "1234", "desc", "rock");
        ListaReproduccion lista = new ListaReproduccion("nombre", "desc");
        perfil.agregarLista(lista);
        assertEquals(1, perfil.getListasReproduccion().size());
        assertEquals(lista, perfil.getListasReproduccion().get(0));
    }

    @Test
    void testParametrosInvalidos() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> new Perfil(null, "Miguel", "1234", "desc", "rock"));
        assertThrows(IllegalArgumentException.class, () -> new Perfil(id, null, "1234", "desc", "rock"));
        assertThrows(IllegalArgumentException.class, () -> new Perfil(id, "", "1234", "desc", "rock"));
        assertThrows(IllegalArgumentException.class, () -> new Perfil(id, "Miguel", null, "desc", "rock"));
        assertThrows(IllegalArgumentException.class, () -> new Perfil(id, "Miguel", "", "desc", "rock"));
        assertThrows(IllegalArgumentException.class, () -> new Perfil(id, "Miguel", "1234", null, "rock"));
        assertThrows(IllegalArgumentException.class, () -> new Perfil(id, "Miguel", "1234", "", "rock"));
        assertThrows(IllegalArgumentException.class, () -> new Perfil(id, "Miguel", "1234", "desc", null));
        assertThrows(IllegalArgumentException.class, () -> new Perfil(id, "Miguel", "1234", "desc", ""));
    }
}
