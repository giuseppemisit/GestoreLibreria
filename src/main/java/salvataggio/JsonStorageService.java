package salvataggio;

import base.libro.Libro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashSet;
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
    public Set<String> caricaUtenti() {
        try {
            File file = new File(percorsoUtenti);
            if (!file.exists()) {
                return new HashSet<>();
            }

            try (FileReader reader = new FileReader(file)) {
                Type type = new TypeToken<Set<String>>() {}.getType();
                Set<String> utenti = gson.fromJson(reader, type);
                return utenti != null ? utenti : new HashSet<>();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento degli utenti", e);
            return new HashSet<>();
        }
    }

    @Override
    public Set<Libro> caricaLibreria() {
        // Se username è null, non possiamo caricare la libreria
        if (percorsoLibreria == null) {
            throw new IllegalStateException("Non è possibile caricare la libreria senza specificare un utente");
        }

        try {
            File file = new File(percorsoLibreria);
            if (!file.exists()) {
                return new HashSet<>();
            }

            try (FileReader reader = new FileReader(file)) {
                Type type = new TypeToken<Set<Libro>>() {}.getType();
                Set<Libro> libreria = gson.fromJson(reader, type);
                return libreria != null ? libreria : new HashSet<>();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento della libreria", e);
            return new HashSet<>();
        }
    }

    @Override
    public boolean salvaUtenti(Set<String> utenti) {
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
    public boolean salvaLibreria(Set<Libro> libreria) {
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

}