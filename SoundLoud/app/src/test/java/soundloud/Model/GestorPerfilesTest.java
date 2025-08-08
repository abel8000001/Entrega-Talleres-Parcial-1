package soundloud.Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import soundloud.Model.GestorPerfiles.GestorPerfiles;
import soundloud.Model.Perfil.Perfil;
import java.util.UUID;
import java.util.List;

class GestorPerfilesTest {
    @Test
    void testAgregarYObtenerPerfiles() {
        GestorPerfiles.getPerfiles().clear();
        Perfil perfil = new Perfil(UUID.randomUUID(), "Miguel", "1234", "desc", "rock");
        GestorPerfiles.getPerfiles().add(perfil);
        assertTrue(GestorPerfiles.getPerfiles().contains(perfil));
    }

    @Test
    void testEliminarPerfil() {
        GestorPerfiles.getPerfiles().clear();
        Perfil perfil = new Perfil(UUID.randomUUID(), "Miguel", "1234", "desc", "rock");
        GestorPerfiles.getPerfiles().add(perfil);
        boolean eliminado = GestorPerfiles.eliminarPerfil(perfil);
        assertTrue(eliminado);
        assertFalse(GestorPerfiles.getPerfiles().contains(perfil));
    }
}
