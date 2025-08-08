package paragon.practica.poo.database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import paragon.practica.poo.model.Jugador;
import paragon.practica.poo.model.Personaje;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Database {
    private static final String ARCHIVO = "database.json";
    private static List<Jugador> jugadores = new ArrayList<>();
            
    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Personaje.class, new PersonajeDeserializer())
        .setPrettyPrinting()
        .create();

    public static void cargar() throws IOException {
        File fichero = new File(ARCHIVO);

        if (!fichero.exists()) {
            jugadores = new ArrayList<>();
            guardar();
            return;
        }

        try (FileReader fr = new FileReader(fichero)) {
            Type type = new TypeToken<List<Jugador>>() {}.getType();
            List<Jugador> data = GSON.fromJson(fr, type);
            jugadores = (data != null) ? data : new ArrayList<>();
        }
    }

    public static void guardar() throws IOException {
        try (FileWriter fw = new FileWriter(ARCHIVO)) {
            GSON.toJson(jugadores, fw);
        }
    }

    public static List<Jugador> getJugadores() {
        return jugadores;
    }
}
