package app.utenti;

import salvataggio.JsonStorageService;
import salvataggio.StorageService;

import java.util.*;

public class ConcreteUserManager implements UserManager {

    private final StorageService storageService;
    private final Set<String> utentiRegistrati;

    public ConcreteUserManager(){
        this.storageService = new JsonStorageService(null);
        Collection<String> utentiCaricati = storageService.caricaUtenti();
        this.utentiRegistrati = new LinkedHashSet<>(utentiCaricati);
    }

    @Override
    public synchronized List<String> utentiRegistrati() {
        return new ArrayList<>(utentiRegistrati);
    }

    @Override
    public synchronized boolean esisteUtente(String username){
        return utentiRegistrati.contains(username);
    }

    @Override
    public synchronized boolean creaNuovoUtente(String username) {
        if(utentiRegistrati.contains(username))
            return false;
        // Crea una libreria vuota per l'utente'
        StorageService fileSalvataggio = new JsonStorageService(username);
        fileSalvataggio.salvaLibreria(new ArrayList<>());
        utentiRegistrati.add(username);
        storageService.salvaUtenti(utentiRegistrati);
        return true;
    }

    @Override
    public synchronized boolean eliminaUtente(String username) {
        if(!utentiRegistrati.contains(username))
            return false;
        StorageService fileSalvataggio = new JsonStorageService(username);
        fileSalvataggio.eliminaLibreria();
        utentiRegistrati.remove(username);
        storageService.salvaUtenti(utentiRegistrati);
        return true;
    }

}
