package paragon.practica.poo.database;

import com.google.gson.*;

import paragon.practica.poo.model.Personaje;
import paragon.practica.poo.model.clasespersonaje.Guerrero;
import paragon.practica.poo.model.clasespersonaje.Mago;
import paragon.practica.poo.model.clasespersonaje.Picaro;

import java.lang.reflect.Type;

public class PersonajeDeserializer implements JsonDeserializer<Personaje> {

    @Override
    public Personaje deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String tipo = obj.get("tipo").getAsString();

        Class<? extends Personaje> clase;
        switch (tipo) {
            case "Guerrero":
                clase = Guerrero.class;
                break;
            case "Mago":
                clase = Mago.class;
                break;
            case "Picaro":
                clase = Picaro.class;
                break;
            default:
                throw new JsonParseException("Tipo desconocido: " + tipo);
        }
        // A este punto, delegamos en Gson para instanciar la subclase con sus campos
        return context.deserialize(obj, clase);
    }
}
