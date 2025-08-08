package soundloud.Model.GestorPerfiles;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import soundloud.Model.Perfil.Perfil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GestorPerfiles {

    private static final Logger logger = LogManager.getLogger(GestorPerfiles.class);

    private static final String RUTA_ARCHIVO = "perfiles.json";
    private static List<Perfil> perfiles = new ArrayList<>();

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    // Guardar un perfil en el archivo
    public static void guardarPerfiles() {
        try (FileWriter fw = new FileWriter(RUTA_ARCHIVO)) {
            GSON.toJson(perfiles, fw);
            logger.info("Perfiles guardados.");
        } catch (IOException e) {
            logger.error("Error al guardar los perfiles: " + e.getMessage());
            System.out.println("Error al guardar los perfiles.");
        }
    }

    // Leer todos los perfiles desde el archivo
    public static void cargarPerfiles() {
        File fichero = new File(RUTA_ARCHIVO);

        if (!fichero.exists()) {
            perfiles = new ArrayList<>();
            guardarPerfiles();
        }

        try (FileReader fr = new FileReader(fichero)) {

            Type type = new TypeToken<List<Perfil>>() {
            }.getType();
            List<Perfil> data = GSON.fromJson(fr, type);
            perfiles = (data != null) ? data : new ArrayList<>();
        } catch (FileNotFoundException e) {
            // El archivo no existe aún, no pasa nada
        } catch (IOException e) {
            logger.error("Error al leer los perfiles: " + e.getMessage());
        }
    }

    // Eliminar un perfil
    public static boolean eliminarPerfil(Perfil perfilAEliminar) {
        cargarPerfiles();
        // Log de IDs para depuración
        logger.info("ID a eliminar: " + perfilAEliminar.getId());
        for (Perfil p : perfiles) {
            logger.info("Perfil cargado: " + p.getNombre() + " | ID: " + p.getId());
        }
        boolean eliminado = perfiles.removeIf(p -> p.getId().equals(perfilAEliminar.getId()));

        if (eliminado) {
            guardarPerfiles(); // Guarda todos los perfiles restantes
            eliminarListasDelPerfil(perfilAEliminar); // (opcional) Limpia sus listas
            logger.info("Perfil eliminado: " + perfilAEliminar.getNombre());
            return true;
        } else {
            logger.warn("No se encontró el perfil para eliminar: " + perfilAEliminar.getNombre());
            return false;
        }
    }

    // Eliminar las listas del perfil si están en un archivo separado
    private static void eliminarListasDelPerfil(Perfil perfil) {
        String ruta = "src/main/resources/Archivos/listas_" + perfil.getId() + ".txt";
        File archivo = new File(ruta);
        if (archivo.exists() && archivo.delete()) {
            logger.info("Listas del perfil eliminadas: " + archivo.getName());
        } else {
            logger.warn("No se encontró archivo de listas para eliminar: " + archivo.getName());
        }
    }

    public static List<Perfil> getPerfiles() {
        return perfiles;
    }

}
