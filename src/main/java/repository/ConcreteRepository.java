package repository;

import base.libro.Libro;
import interrogazione.ParametriFiltro;
import interrogazione.ParametriRicerca;
import ordinamento.Ordinamento;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConcreteRepository extends AbstractRepository{

    private String percorsoFile;
    private final ObjectMapper objectMapper;

    public ConcreteRepository(String username){
        super(username);
    }


    @Override
    public Libro nuoviValoriAggiornamento() {
        return null;
    }

    @Override
    public Ordinamento scegliOrdinamento() {
        return null;
    }

    @Override
    public void scegliFiltri(ParametriFiltro filtri) {

    }

    @Override
    public void scegliRicerca(ParametriRicerca ricerca) {

    }

    @Override
    public boolean salvaDati(String username, List<Libro> libri) {
        return false;
    }

    @Override
    public List<Libro> leggiDati(String username) {

        List<Libro> libri = new ArrayList<>();

        this.percorsoFile = "/Users/giuseppegiulio/IdeaProjects/GestoreLibreria/datiSalvati/" + username + ".json";
        File file = new File(percorsoFile);

        if (!file.exists()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("L'utente " + getUsername() + " non è stato trovato." +
                    "\nVuoi crearne uno nuovo con questo nome? (s/n): ");
            String risposta = scanner.nextLine().trim().toLowerCase();

            if ("s".equals(risposta) || "si".equals(risposta)) {
                creaNuovoUtente(file, libri);
            } else {
                System.out.println("Operazione annullata dall'utente.");
                throw new IllegalStateException("Utente non esistente e creazione annullata.");
            }
        }
        else {
            System.out.println("Ben tornato " + getUsername());

            try {
                // Verifica che il file non sia vuoto
                if (file.length() > 0) {
                    // Leggi i dati dal file JSON e convertili in una lista di Libro
                    libri = objectMapper.readValue(file, new TypeReference<List<Libro>>() {});
                    System.out.println("Dati caricati con successo dal file.");
                } else {
                    System.out.println("Il file dell'utente esiste ma è vuoto.");
                }
            } catch (IOException e) {
                System.err.println("Errore durante la lettura del file: " + e.getMessage());
            }
        }
        return libri;
    }


    private void creaNuovoUtente(File file, List<Libro> libri){
        try {
            // Controllo che la cartella esista
            File dir = file.getParentFile();
            if (!dir.exists())
                dir.mkdirs();

            // Creazione del file vuoto
            if (file.createNewFile()) {
                System.out.println("Utente " + getUsername() + " creato correttamente!");
                salvaDati(getUsername(), libri);
            } else {
                System.err.println("Errore durante la creazione del nuovo utente " + getUsername());
            }
        } catch (IOException e) {
            System.err.println("Impossibile creare il file: " + e.getMessage());
        }
    }



}
