package salvataggio;

import base.libro.Libro;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JsonStorageService implements StorageService {

    private static final Logger LOGGER = Logger.getLogger(JsonStorageService.class.getName());
    private static final String cartella = "datiSalvati/";

    private final String percorsoUtenti;
    private final String percorsoLibreria;

    private final Gson gson;


    public JsonStorageService(String username) {
        Objects.requireNonNull(username, "Il nome utente non può essere null");
        this.percorsoUtenti = cartella.concat("utenti.json");
        this.percorsoLibreria = cartella.concat(username).concat(".json");

        this.gson = new Gson();
    }


    @Override
    public Set<String> caricaUtenti() {
        File file = new File(percorsoUtenti);
        if (!file.exists()) {
            LOGGER.info("File utenti non trovato, restituisco un set vuoto");
            return new HashSet<>();
        }

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Set<String>>(){}.getType();
            Set<String> utenti = gson.fromJson(reader, type);
            return utenti != null ? utenti : new HashSet<>();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento degli utenti", e);
            return new HashSet<>();
        }
    }

    @Override
    public Set<Libro> caricaLibreria(){
        File file = new File(percorsoLibreria);
        if (!file.exists()) {
            LOGGER.info("File libreria non trovato, restituisco un set vuoto");
            return new HashSet<>();
        }

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Set<Libro>>(){}.getType();
            Set<Libro> libreria = gson.fromJson(reader, type);
            return libreria != null ? libreria : new HashSet<>();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento della libreria", e);
            return new HashSet<>();
        }
    }



    @Override
    public boolean salvaUtenti(Set<String> utenti) {
        Objects.requireNonNull(utenti, "Il set di utenti non può essere null");

        try {
            File file = new File(percorsoUtenti);
            File directory = file.getParentFile();

            // Creo la directory se non esiste
            if (directory != null && !directory.exists() && !directory.mkdirs()) {
                LOGGER.severe("Impossibile creare la directory per il salvataggio degli utenti");
                return false;
            }

            // Converto il set in JSON e lo salvo
            try (Writer writer = new FileWriter(file)) {
                gson.toJson(utenti, writer);
                return true;
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il salvataggio degli utenti", e);
            return false;
        }
    }

    @Override
    public boolean salvaLibreria(Set<Libro> libreria){
        Objects.requireNonNull(libreria, "La libreria non può essere null");

        try {
            File file = new File(percorsoLibreria);
            File directory = file.getParentFile();

            // Creo la directory se non esiste
            if (directory != null && !directory.exists() && !directory.mkdirs()) {
                LOGGER.severe("Impossibile creare la directory per il salvataggio della libreria");
                return false;
            }

            try (Writer writer = new FileWriter(file)) {
                gson.toJson(libreria, writer);
                return true;
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il salvataggio della libreria", e);
            return false;
        }
    }

    @Override
    public boolean eliminaLibreria(){
        try {
            File file = new File(percorsoLibreria);

            if (!file.exists()) {
                LOGGER.info("File libreria già non esistente durante l'eliminazione");
                return false;
            }
            boolean deleted = file.delete();
            if (!deleted) {
                LOGGER.warning("Impossibile eliminare il file della libreria");
            }
            return deleted;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'eliminazione della libreria", e);
            return false;
        }
    }

}
