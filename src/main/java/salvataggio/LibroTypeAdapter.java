package salvataggio;

import base.libro.ConcreteLibro;
import base.libro.Libro;
import base.utility.Autore;
import base.utility.InfoExtra;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

public class LibroTypeAdapter implements JsonSerializer<Libro>, JsonDeserializer<Libro> {

    @Override
    public JsonElement serialize(Libro libro, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        
        aggiungiProprietaBase(jsonObject, libro);
        aggiungiValutazione(jsonObject, libro);
        aggiungiStatoLettura(jsonObject, libro);
        aggiungiAutori(jsonObject, libro);
        aggiungiGeneri(jsonObject, libro);
        
        return jsonObject;
    }

    private void aggiungiProprietaBase(JsonObject jsonObject, Libro libro) {
        jsonObject.addProperty("isbn", libro.getIsbn());
        jsonObject.addProperty("titolo", libro.getTitolo());
    }

    private void aggiungiValutazione(JsonObject jsonObject, Libro libro) {
        if (libro.getValutazione() != null) {
            jsonObject.addProperty("valutazione", libro.getValutazione().getStelle());
        }
    }

    private void aggiungiStatoLettura(JsonObject jsonObject, Libro libro) {
        if (libro.getStatoLettura() != null) {
            jsonObject.addProperty("statoLettura", libro.getStatoLettura().name());
        }
    }

    private void aggiungiAutori(JsonObject jsonObject, Libro libro) {
        JsonArray autoriArray = new JsonArray();
        for (Autore autore : libro.getAutori()) {
            JsonObject autoreObj = new JsonObject();
            autoreObj.addProperty("nome", autore.getNome());
            autoreObj.addProperty("cognome", autore.getCognome());
            autoriArray.add(autoreObj);
        }
        jsonObject.add("autori", autoriArray);
    }

    private void aggiungiGeneri(JsonObject jsonObject, Libro libro) {
        JsonArray generiArray = new JsonArray();
        for (InfoExtra.GenereLibro genere : libro.getGeneri()) {
            generiArray.add(genere.name());
        }
        jsonObject.add("generi", generiArray);
    }

    
    @Override
    public Libro deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String isbn = estraiStringa(jsonObject, "isbn");
        String titolo = estraiStringa(jsonObject, "titolo");
        InfoExtra.Valutazione valutazione = estraiValutazione(jsonObject);
        InfoExtra.StatoLettura statoLettura = estraiStatoLettura(jsonObject);
        Set<Autore> autori = estraiAutori(jsonObject);
        Set<InfoExtra.GenereLibro> generi = estraiGeneri(jsonObject);

        return new ConcreteLibro(isbn, titolo, valutazione, statoLettura, autori, generi);
    }

    private String estraiStringa(JsonObject jsonObject, String nomeCampo) {
        return jsonObject.get(nomeCampo).getAsString();
    }

    private InfoExtra.Valutazione estraiValutazione(JsonObject jsonObject) {
        if (jsonObject.has("valutazione")) {
            JsonElement valElement = jsonObject.get("valutazione");
            if (valElement.isJsonObject()) {
                JsonObject valObj = valElement.getAsJsonObject();
                if (valObj.has("stelle") && valObj.get("stelle").isJsonPrimitive()) {
                    int stelle = valObj.get("stelle").getAsInt();
                    return new InfoExtra.Valutazione(stelle);
                }
            }
        }
        return null;
    }


    private InfoExtra.StatoLettura estraiStatoLettura(JsonObject jsonObject) {
        if (jsonObject.has("statoLettura")) {
            String statoLetturaStr = jsonObject.get("statoLettura").getAsString();
            return InfoExtra.StatoLettura.valueOf(statoLetturaStr);
        }
        return null;
    }

    private Set<Autore> estraiAutori(JsonObject jsonObject) {
        Set<Autore> autori = new LinkedHashSet<>();
        if (jsonObject.has("autori")) {
            JsonArray autoriArray = jsonObject.getAsJsonArray("autori");
            for (JsonElement element : autoriArray) {
                JsonObject autoreObj = element.getAsJsonObject();
                String nome = autoreObj.get("nome").getAsString();
                String cognome = autoreObj.get("cognome").getAsString();
                autori.add(new Autore(nome, cognome));
            }
        }
        return autori;
    }

    private Set<InfoExtra.GenereLibro> estraiGeneri(JsonObject jsonObject) {
        Set<InfoExtra.GenereLibro> generi = new LinkedHashSet<>();
        if (jsonObject.has("generi")) {
            JsonArray generiArray = jsonObject.getAsJsonArray("generi");
            for (JsonElement element : generiArray) {
                String genereStr = element.getAsString();
                generi.add(InfoExtra.GenereLibro.valueOf(genereStr));
            }
        }
        return generi;
    }

}