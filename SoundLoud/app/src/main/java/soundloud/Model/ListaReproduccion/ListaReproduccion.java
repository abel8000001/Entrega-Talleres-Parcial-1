package soundloud.Model.ListaReproduccion;

import soundloud.Model.Cancion.Cancion;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;   
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Desktop;
import java.net.URI;

public class ListaReproduccion {

    private static final Logger logger = LogManager.getLogger(ListaReproduccion.class);

    private UUID id;
    private String nombre;
    private String descripcion;
    private ArrayList<Cancion> canciones;

    public ListaReproduccion(String nombre, String descripcion) {
        try {
            if (nombre == null || descripcion == null) {
                logger.error("Los parámetros no pueden ser nulos");
                throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
            }
            if (nombre.isEmpty() || descripcion.isEmpty()) {
                logger.error("Los parámetros no pueden estar vacíos");
                throw new IllegalArgumentException("Los parámetros no pueden estar vacíos");
            }

            this.id = UUID.randomUUID();
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.canciones = new ArrayList<>();

        } catch (Exception e) {
            logger.error("Error al crear la lista de reproducción: " + e.getMessage());
            throw new RuntimeException("Error al crear la lista de reproducción: " + e.getMessage());
        }
    }

    public void agregarCancion(Cancion cancion) {
        canciones.add(cancion);
    }

    public void mostrarCanciones() {
        if (canciones.isEmpty()) {
            System.out.println("La lista está vacía.");
        } else {
            System.out.println("\n--- Canciones en la lista \"" + nombre + "\" ---");
            for (int i = 0; i < canciones.size(); i++) {
                System.out.println((i + 1) + ". " + canciones.get(i));
            }
        }
    }

    public void cargarDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int lineaActual = 0;

            while ((linea = br.readLine()) != null) {
                lineaActual++;
                String[] partes = linea.split("\\|");

                if (partes.length != 4) {
                    logger.warn("Línea mal formateada en " + rutaArchivo + " (línea " + lineaActual + "): " + linea);
                    continue;
                }

                String nombreCancion = partes[0].trim();
                String artista = partes[1].trim();
                String duracion = partes[2].trim();
                String url = partes[3].trim();

                Cancion cancion = new Cancion(nombreCancion, artista, duracion, url);
                agregarCancion(cancion);
            }

            logger.info("Canciones cargadas exitosamente desde: " + rutaArchivo);
        } catch (IOException e) {
            logger.error("Error al leer el archivo de canciones: " + e.getMessage());
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    //Reproducir una canción por índice
    public void reproducirCancion(int indice) {
        if (indice < 0 || indice >= canciones.size()) {
            System.out.println("Índice de canción inválido.");
            return;
        }

        Cancion cancion = canciones.get(indice);
        System.out.println("Reproduciendo: " + cancion.getTitulo() + " de " + cancion.getArtista());

        try {
            Desktop.getDesktop().browse(new URI(cancion.getUrlYouTube()));
        } catch (Exception e) {
            logger.error("Error al abrir la URL de la canción: " + e.getMessage());
            System.out.println("No se pudo abrir la canción.");
        }
    }

    //Reproducir la playlist completa esperando la acción del usuario
    public void reproducirPlaylistCompleta() {
    if (canciones.isEmpty()) {
        System.out.println("La lista está vacía.");
        return;
    }

    Scanner scanner = new Scanner(System.in); // Scanner para leer la entrada del usuario

    try {
        for (Cancion cancion : canciones) {
            System.out.println("Reproduciendo: " + cancion.getTitulo() + " de " + cancion.getArtista());
            Desktop.getDesktop().browse(new URI(cancion.getUrlYouTube()));

            System.out.println("Presiona ENTER para pasar a la siguiente canción...");
            scanner.nextLine(); // Espera a que el usuario presione ENTER
        }
    } catch (Exception e) {
        logger.error("Error al reproducir la playlist: " + e.getMessage());
        System.out.println("No se pudo reproducir la playlist completa.");
    }
}

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }
}
