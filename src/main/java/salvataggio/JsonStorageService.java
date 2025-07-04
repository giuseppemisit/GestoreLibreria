package salvataggio;

import base.libro.Libro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonStorageService implements StorageService {

    private static final Logger LOGGER = Logger.getLogger(JsonStorageService.class.getName());
    private static final String cartella = "datiSalvati/";
    private static final String UTENTI_FILE = "utenti.json";

    private final String percorsoUtenti;
    private final String percorsoLibreria;

    private final Gson gson;

    public JsonStorageService(String username) {
        // Crea la cartella datiSalvati se non esiste
        File dir = new File(cartella);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        percorsoUtenti = cartella + UTENTI_FILE;

        // Se username è null, stiamo solo accedendo alla lista utenti
        // quindi non importa il percorso della libreria
        percorsoLibreria = username != null ? cartella + username + ".json" : null;

        gson = new GsonBuilder()
                .registerTypeAdapter(Libro.class, new LibroTypeAdapter())
                .create();
    }


    @Override
    public Collection<String> caricaUtenti() {
        try {
            File file = new File(percorsoUtenti);
            if (!file.exists()) {
                return new ArrayList<>();
            }

            try (FileReader reader = new FileReader(file)) {
                Type type = new TypeToken<Set<String>>() {}.getType();
                Collection<String> utenti = gson.fromJson(reader, type);
                return utenti != null ? utenti : new ArrayList<>();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento degli utenti", e);
            return new ArrayList<>();
        }
    }

    @Override
    public Collection<Libro> caricaLibreria() {
        // Se username è null, non possiamo caricare la libreria
        if (percorsoLibreria == null) {
            throw new IllegalStateException("Non è possibile caricare la libreria senza specificare un utente");
        }

        try {
            File file = new File(percorsoLibreria);
            if (!file.exists()) {
                return new ArrayList<>();
            }

            try (FileReader reader = new FileReader(file)) {
                Type type = new TypeToken<List<Libro>>() {}.getType();
                Collection<Libro> libreria = gson.fromJson(reader, type);
                return libreria != null ? libreria : new ArrayList<>();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento della libreria", e);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean salvaUtenti(Collection<String> utenti) {
        try {
            try (FileWriter writer = new FileWriter(percorsoUtenti)) {
                gson.toJson(utenti, writer);
                return true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante il salvataggio degli utenti", e);
            return false;
        }
    }

    @Override
    public boolean salvaLibreria(Collection<Libro> libreria) {
        // Se username è null, non possiamo salvare la libreria
        if (percorsoLibreria == null) {
            throw new IllegalStateException("Non è possibile salvare la libreria senza specificare un utente");
        }

        try {
            try (FileWriter writer = new FileWriter(percorsoLibreria)) {
                gson.toJson(libreria, writer);
                return true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante il salvataggio della libreria", e);
            return false;
        }
    }

    @Override
    public boolean eliminaLibreria() {
        // Se username è null, non possiamo eliminare la libreria
        if (percorsoLibreria == null) {
            throw new IllegalStateException("Non è possibile eliminare la libreria senza specificare un utente");
        }

        File file = new File(percorsoLibreria);
        if (file.exists()) {
            return file.delete();
        }
        return true; // Se il file non esiste, l'operazione è considerata riuscita
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof JsonStorageService)) return false;

        JsonStorageService s = (JsonStorageService) obj;
        return s.percorsoUtenti.equals(this.percorsoUtenti) &&
                s.percorsoLibreria.equals(this.percorsoLibreria);
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + percorsoUtenti.hashCode();
        result = prime * result + percorsoLibreria.hashCode();
        return result;
    }
}