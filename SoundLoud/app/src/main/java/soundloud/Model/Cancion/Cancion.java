package soundloud.Model.Cancion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cancion {

    private static final Logger logger = LogManager.getLogger(Cancion.class);

    private String titulo;
    private String artista;
    private String duracion;
    private String urlYouTube;

    public Cancion(String titulo, String artista, String duracion, String urlYouTube) {
        try {
            if (titulo == null || artista == null || urlYouTube == null || duracion == null) {
                logger.error("Los parámetros no pueden ser nulos");
                throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
            }
            if (titulo.isEmpty() || artista.isEmpty() || urlYouTube.isEmpty() || duracion.isEmpty()) {
                logger.error("Los parámetros no pueden estar vacíos");
                throw new IllegalArgumentException("Los parámetros no pueden estar vacíos");
            }

            logger.info("Creando canción: " + titulo + " de " + artista);

            this.titulo = titulo;
            this.artista = artista;
            this.duracion = duracion;
            this.urlYouTube = urlYouTube;

        } catch (Exception e) {
            logger.error("Error al crear la canción: " + e.getMessage());
            throw new RuntimeException("Error al crear la canción: " + e.getMessage());
        }
        
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getUrlYouTube() {
        return urlYouTube;
    }

    @Override
    public String toString() {
        return titulo + " - " + artista + " (" + duracion + " min)";
    }
}

