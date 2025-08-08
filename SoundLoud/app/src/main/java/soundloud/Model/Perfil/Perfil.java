package soundloud.Model.Perfil;

import java.util.UUID;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import soundloud.Model.Cancion.Cancion;
import soundloud.Model.ListaReproduccion.ListaReproduccion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Perfil {

    private static final Logger logger = LogManager.getLogger(Perfil.class);

    private UUID id;
    private String nombre;
    private String contra;
    private String descripcion;
    private String generoFavorito;
    private transient Path ruta;
    private transient String rutaFinal;

    // Listas de reproducción del perfil
    private ArrayList<ListaReproduccion> listasReproduccion;

    public Perfil(UUID id, String nombre, String contra, String descripcion, String generoFavorito) {
        try {
            if (id == null || nombre == null || descripcion == null || generoFavorito == null || contra == null) {
                logger.error("Los parámetros no pueden ser nulos");
                throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
            }
            if (nombre.isEmpty() || descripcion.isEmpty() || generoFavorito.isEmpty() || contra.isEmpty()) {
                logger.error("Los parámetros no pueden estar vacíos");
                throw new IllegalArgumentException("Los parámetros no pueden estar vacíos");
            }

            logger.info("Creando perfil con ID: " + id);

            this.id = id; // o usa this.id = id;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.generoFavorito = generoFavorito;
            this.contra = contra;
            this.listasReproduccion = new ArrayList<>(); // Inicializa la colección

        } catch (Exception e) {
            logger.error("Error al crear el perfil: " + e.getMessage());
            throw new RuntimeException("Error al crear el perfil: " + e.getMessage());
        }
    }

    // Agregar una lista
    public void agregarLista(ListaReproduccion lista) {
        if (lista != null) {
            listasReproduccion.add(lista);
            logger.info("Lista agregada: " + lista.getNombre());
        }
    }

    // Metodo para guardar las Listas en el perfil del usuario
    public void guardarListas() {
        ruta = Paths.get("src", "main", "resources", "Archivos", this.nombre + "_listas.txt");
        rutaFinal = ruta.toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaFinal))) {
            for (ListaReproduccion lista : listasReproduccion) {
                writer.write(lista.getNombre() + "|" + lista.getDescripcion());
                writer.newLine();

                for (Cancion c : lista.getCanciones()) {
                    writer.write(
                            c.getTitulo() + "|" + c.getArtista() + "|" + c.getDuracion() + "|" + c.getUrlYouTube());
                    writer.newLine();
                }

                writer.write("---"); // Separador entre listas
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo para cargar las Listas del usuario al iniciar sesion

    public void cargarListas() {
        ruta = Paths.get("src", "main", "resources", "Archivos", this.nombre + "_listas.txt");
        rutaFinal = ruta.toString();
        File archivo = new File(rutaFinal);
        if (!archivo.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            ListaReproduccion listaActual = null;

            while ((linea = reader.readLine()) != null) {
                if (linea.equals("---")) {
                    if (listaActual != null) {
                        listasReproduccion.add(listaActual);
                        listaActual = null;
                    }
                    continue;
                }

                String[] partes = linea.split("\\|");

                if (partes.length == 2) {
                    // Es una nueva lista
                    listaActual = new ListaReproduccion(partes[0].trim(), partes[1].trim());
                } else if (partes.length == 4 && listaActual != null) {
                    Cancion cancion = new Cancion(
                            partes[0].trim(),
                            partes[1].trim(),
                            partes[2].trim(),
                            partes[3].trim());
                    listaActual.agregarCancion(cancion);
                }
            }

            if (listaActual != null) {
                listasReproduccion.add(listaActual);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Obtener todas las listas
    public ArrayList<ListaReproduccion> getListasReproduccion() {
        return listasReproduccion;
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGeneroFavorito() {
        return generoFavorito;
    }

    public String getContrasena() {
        return contra;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", generoFavorito='" + generoFavorito + '\'' +
                ", contra='" + contra + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Perfil))
            return false;

        Perfil perfil = (Perfil) o;

        return id.equals(perfil.id);
    }
}
