package salvataggio;

import base.libro.ConcreteLibro;
import base.libro.Libro;
import base.utility.Autore;
import base.utility.InfoExtra;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;


public class LibroTypeAdapter implements JsonSerializer<Libro>, JsonDeserializer<Libro> {

    @Override
    public JsonElement serialize(Libro libro, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        
        // Serializzazione dei campi del libro
        jsonObject.addProperty("isbn", libro.getIsbn());
        jsonObject.addProperty("titolo", libro.getTitolo());
        
        // Serializzazione di valutazione
        if (libro.getValutazione() != null) {
            jsonObject.addProperty("valutazione", libro.getValutazione().getStelle());
        }
        
        // Serializzazione di statoLettura
        if (libro.getStatoLettura() != null) {
            jsonObject.addProperty("statoLettura", libro.getStatoLettura().name());
        }
        
        // Serializzazione degli autori
        JsonArray autoriArray = new JsonArray();
        for (Autore autore : libro.getAutori()) {
            JsonObject autoreObj = new JsonObject();
            autoreObj.addProperty("nome", autore.getNome());
            autoreObj.addProperty("cognome", autore.getCognome());
            autoriArray.add(autoreObj);
        }
        jsonObject.add("autori", autoriArray);
        
        // Serializzazione dei generi
        JsonArray generiArray = new JsonArray();
        for (InfoExtra.GenereLibro genere : libro.getGeneri()) {
            generiArray.add(genere.name());
        }
        jsonObject.add("generi", generiArray);
        
        return jsonObject;
    }

    @Override
    public Libro deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        
        // Estrazione dei campi base
        String isbn = jsonObject.get("isbn").getAsString();
        String titolo = jsonObject.get("titolo").getAsString();
        
        // Estrazione della valutazione
        InfoExtra.Valutazione valutazione = null;
        if (jsonObject.has("valutazione")) {
            int stelle = jsonObject.get("valutazione").getAsInt();
            valutazione = new InfoExtra.Valutazione(stelle);
        }
        
        // Estrazione dello stato di lettura
        InfoExtra.StatoLettura statoLettura = null;
        if (jsonObject.has("statoLettura")) {
            String statoLetturaStr = jsonObject.get("statoLettura").getAsString();
            statoLettura = InfoExtra.StatoLettura.valueOf(statoLetturaStr);
        }
        
        // Estrazione degli autori
        Set<Autore> autori = new HashSet<>();
        if (jsonObject.has("autori")) {
            JsonArray autoriArray = jsonObject.getAsJsonArray("autori");
            for (JsonElement element : autoriArray) {
                JsonObject autoreObj = element.getAsJsonObject();
                String nome = autoreObj.get("nome").getAsString();
                String cognome = autoreObj.get("cognome").getAsString();
                autori.add(new Autore(nome, cognome));
            }
        }
        
        // Estrazione dei generi
        Set<InfoExtra.GenereLibro> generi = new HashSet<>();
        if (jsonObject.has("generi")) {
            JsonArray generiArray = jsonObject.getAsJsonArray("generi");
            for (JsonElement element : generiArray) {
                String genereStr = element.getAsString();
                generi.add(InfoExtra.GenereLibro.valueOf(genereStr));
            }
        }
        
        // Creazione dell'oggetto Libro concreto
        return new ConcreteLibro(isbn, titolo, valutazione, statoLettura, autori, generi);
    }

}