package soundloud;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import soundloud.Model.Cancion.Cancion;
import soundloud.Model.Colas.ColaReproduccion;
import soundloud.Model.GestorPerfiles.GestorPerfiles;
import soundloud.Model.ListaReproduccion.ListaReproduccion;
import soundloud.Model.Perfil.Perfil;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorPerfiles.cargarPerfiles();
        Perfil perfilAutenticado = null;

        System.out.println("Bienvenido a SoundLoud!");
        System.out.println("\nPara ingresar a SoundLoud debes crear o iniciar sesión en tu perfil.");
        System.out.println("\n¿Qué deseas hacer?\n1. Crear un perfil\n2. Iniciar sesión en un perfil existente\n3. Salir");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        System.out.println(" ");

        switch (opcion) {
            case 1:
                System.out.println("Ingrese su nombre:");
                String nombre = scanner.nextLine();
                System.out.println("Ingrese una contraseña:");
                String contra = scanner.nextLine();
                System.out.println("Ingrese una descripción:");
                String descripcion = scanner.nextLine();
                System.out.println("Ingrese su género musical favorito:");
                String genero = scanner.nextLine();

                Perfil nuevoPerfil = new Perfil(UUID.randomUUID(), nombre, contra, descripcion, genero);
                GestorPerfiles.getPerfiles().add(nuevoPerfil);
                GestorPerfiles.guardarPerfiles();
                perfilAutenticado = nuevoPerfil;
                System.out.println("Perfil creado exitosamente.");
                break;

            case 2:
                System.out.println("Ingrese su nombre de usuario:");
                String usuario = scanner.nextLine().trim();
                System.out.println("Ingrese su contraseña:");
                String pass = scanner.nextLine().trim();

                List<Perfil> perfiles = GestorPerfiles.getPerfiles();
                for (Perfil p : perfiles) {
                    if (p.getNombre().equals(usuario) && p.getContrasena().equals(pass)) {
                        perfilAutenticado = p;
                         p.cargarListas();
                        System.out.println("\nInicio de sesión exitoso. Bienvenido " + p.getNombre());
                        break;
                    }
                }

                if (perfilAutenticado == null) {
                    System.out.println("Usuario o contraseña incorrectos.");
                    return;
                }
                break;

            case 3:
                System.out.println("Gracias por usar SoundLoud. ¡Hasta luego!");
                return;

            default:
                System.out.println("Opción no válida.");
                return;
        }

        mostrarMenuPrincipal(scanner, perfilAutenticado);
    }

    public static void mostrarMenuPrincipal(Scanner scanner, Perfil usuario) {
        int opcion;
        ColaReproduccion cola = new ColaReproduccion();

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Ver mi perfil");
            System.out.println("2. Crear lista de reproducción");
            System.out.println("3. Ver listas de reproducción");
            System.out.println("4. Agregar canciones a una cola");
            System.out.println("5. Reproducir cola");
            System.out.println("6. Cerrar sesión");
            System.out.println("7. Eliminar mi perfil");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            System.out.println(" ");

            switch (opcion) {
                case 1:
                    System.out.println("\n--- MI PERFIL ---");
                    System.out.println("Nombre: " + usuario.getNombre());
                    System.out.println("Descripción: " + usuario.getDescripcion());
                    System.out.println("Género favorito: " + usuario.getGeneroFavorito());
                    break;

                case 2:
                    crearListaReproduccion(scanner, usuario);
                    break;

                case 3:
                    verListasReproduccion(scanner, usuario);
                    break;

                case 4:
                    agregarCancionesACola(scanner, usuario, cola);
                    break;
                case 5:
                    reproducirCola(cola);
                    break;

                case 6:
                    System.out.println("Sesión cerrada.");
                    break;
                case 7:
                    System.out.println("\n¿Estás seguro que deseas eliminar tu perfil? Esta acción no se puede deshacer. (s/n): ");
                    String confirmacion = scanner.nextLine();
                    if (confirmacion.equalsIgnoreCase("s")) {
                        if (GestorPerfiles.eliminarPerfil(usuario)) {
                            System.out.println("Perfil eliminado exitosamente. Cerrando sesión...");
                            return;
                        } else {
                            System.out.println("No se pudo eliminar el perfil.");
                        }
                    }
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 6);
    }

    public static void crearListaReproduccion(Scanner scanner, Perfil usuario) {
        System.out.println("\nNombre de la nueva lista:");
        String nombre = scanner.nextLine();
        System.out.println("Descripción de la lista:");
        String descripcion = scanner.nextLine();

        ListaReproduccion nuevaLista = new ListaReproduccion(nombre, descripcion);
        usuario.agregarLista(nuevaLista);

        System.out.println("¿Deseas agregar canciones desde un archivo .txt? (s/n):");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("s")) {
            System.out.println("Ingresa la ruta del archivo:");
            String ruta = scanner.nextLine();
            nuevaLista.cargarDesdeArchivo(ruta);
            usuario.guardarListas();
        } else {
            agregarCancionesManual(scanner, nuevaLista, usuario);
        }

        System.out.println("\nLista creada y cargada correctamente.");
        GestorPerfiles.guardarPerfiles();
    }

    public static void agregarCancionesManual(Scanner scanner, ListaReproduccion lista, Perfil usuario) {
        String continuar;
        do {
            System.out.println("Nombre de la canción:");
            String nombre = scanner.nextLine();
            System.out.println("Artista:");
            String artista = scanner.nextLine();
            System.out.println("Duración:");
            String duracion = scanner.nextLine();
            System.out.println("URL de YouTube:");
            String url = scanner.nextLine();

            Cancion nueva = new Cancion(nombre, artista, duracion, url);
            lista.agregarCancion(nueva);

            System.out.println("¿Agregar otra canción? (s/n):");
            continuar = scanner.nextLine();
            if (continuar.equalsIgnoreCase("n")) {
                usuario.guardarListas();
            }

        } while (continuar.equalsIgnoreCase("s"));
    }

    //Ver listas, canciones y reproducir una o toda la playlist
    public static void verListasReproduccion(Scanner scanner, Perfil usuario) {
    ArrayList<ListaReproduccion> listas = usuario.getListasReproduccion();

    if (listas.isEmpty()) {
        System.out.println("No tienes listas creadas.");
        return;
    }

    for (int i = 0; i < listas.size(); i++) {
        System.out.println((i + 1) + ". " + listas.get(i).getNombre());
    }

    System.out.println("\nSelecciona una lista para ver sus canciones (0 para volver):");
    int eleccion = scanner.nextInt();
    scanner.nextLine();

    if (eleccion > 0 && eleccion <= listas.size()) {
        ListaReproduccion listaSeleccionada = listas.get(eleccion - 1);
        listaSeleccionada.mostrarCanciones();

        if (listaSeleccionada.getCanciones().isEmpty()) return;

        System.out.println("\n¿Qué deseas hacer?");
        System.out.println("1. Reproducir una canción");
        System.out.println("2. Reproducir toda la playlist");
        System.out.println("3. Volver");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        System.out.println(" ");

        switch (opcion) {
            case 1:
                System.out.println("\nSelecciona una canción para reproducir (0 para volver):");
                int cancionSeleccionada = scanner.nextInt();
                scanner.nextLine();

                if (cancionSeleccionada > 0 && cancionSeleccionada <= listaSeleccionada.getCanciones().size()) {
                    listaSeleccionada.reproducirCancion(cancionSeleccionada - 1);
                } else {
                    System.out.println("Selección inválida.");
                }
                break;

            case 2:
                listaSeleccionada.reproducirPlaylistCompleta();
                break;

            case 3:
                return;

            default:
                System.out.println("Opción no válida.");
        }
    }
}

    // Selección de canciones a la cola de reproducción
    public static void agregarCancionesACola(Scanner scanner, Perfil perfil, ColaReproduccion cola) {
        System.out.println("\nListas disponibles:");
        for (int i = 0; i < perfil.getListasReproduccion().size(); i++) {
            System.out.println((i + 1) + ". " + perfil.getListasReproduccion().get(i).getNombre());
        }

        System.out.print("\nSeleccione el número de la lista de reproducción: ");
        int numLista = Integer.parseInt(scanner.nextLine());
        ListaReproduccion listaSeleccionada = perfil.getListasReproduccion().get(numLista - 1);

        String opcion;
        do {
            System.out.println("\nCanciones disponibles en " + listaSeleccionada.getNombre() + ":");
            for (int i = 0; i < listaSeleccionada.getCanciones().size(); i++) {
                Cancion c = listaSeleccionada.getCanciones().get(i);
                System.out.println((i + 1) + ". " + c.getTitulo() + " - " + c.getArtista());
            }

            System.out.print("\nSeleccione el número de la canción que desea agregar a la cola: ");
            int numCancion = Integer.parseInt(scanner.nextLine());
            Cancion seleccionada = listaSeleccionada.getCanciones().get(numCancion - 1);
            cola.agregarCancion(seleccionada);
            System.out.println("Canción agregada a la cola.");

            System.out.print("\n¿Desea agregar otra canción? (s/n): ");
            opcion = scanner.nextLine();

        } while (opcion.equalsIgnoreCase("s"));


    }

    // Reproducir canciones de la cola en orden
    public static void reproducirCola(ColaReproduccion cola) {
        if (cola.estaVacia()) {
            System.out.println("La cola de reproducción está vacía.");
            return;
        }

        System.out.println("Reproduciendo canciones...");
        while (!cola.estaVacia()) {
            Cancion actual = cola.reproducirSiguiente();
            System.out.println("\nReproduciendo: " + actual.getTitulo());
            System.out.println("Artista: " + actual.getArtista());
            reproducirCancion(actual.getUrlYouTube());
        }

        System.out.println("\nFin de la cola de reproducción.");
    }

    // Método para reproducir una canción desde su URL y esperar a que el usuario presione ENTER para continuar
    public static void reproducirCancion(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("Presiona ENTER para pasar a la siguiente canción...");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine(); // Espera a que el usuario presione ENTER
            } else {
                System.out.println("Tu sistema no soporta abrir el navegador automáticamente.");
            }
        } catch (Exception e) {
            System.out.println("Error al abrir la canción: " + e.getMessage());
        }
    }

}
