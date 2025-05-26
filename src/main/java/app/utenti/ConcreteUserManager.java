package app.utenti;

import base.libro.Libro;
import salvataggio.JsonStorageService;
import salvataggio.StorageService;

import java.util.HashSet;
import java.util.Set;

public class ConcreteUserManager implements UserManager {

    private final StorageService storageService;
    private final Set<String> utentiRegistrati;

    public ConcreteUserManager(){
        this.storageService = new JsonStorageService(null);
        this.utentiRegistrati = storageService.caricaUtenti();
    }

    @Override
    public synchronized Set<String> utentiRegistrati() {
        return new HashSet<>(utentiRegistrati);
    }

    @Override
    public synchronized boolean esisteUtente(String username){
        return utentiRegistrati.contains(username);
    }

    @Override
    public synchronized boolean creaNuovoUtente(String username) {
        if(utentiRegistrati.contains(username))
            return false;
        Set<Libro> fileVuoto = new HashSet<>();
        StorageService fileSalvataggio = new JsonStorageService(username);
        fileSalvataggio.salvaLibreria(fileVuoto);
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
