package base.libreria;

import base.libro.Libro;
import salvataggio.StorageService;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractLibreria implements Libreria {

    private final StorageService storageService;

    private final Set<String> utentiRegistrati;

    private final String utenteCorrente;
    private final Set<Libro> libriUtente;


    public AbstractLibreria(StorageService storageService, String utenteCorrente) {
        Objects.requireNonNull(storageService, "StorageService non può essere null");
        Objects.requireNonNull(utenteCorrente, "L'utente corrente non può essere null");

        this.storageService = storageService;
        utentiRegistrati = storageService.caricaUtenti();

        if(!utentiRegistrati.contains(utenteCorrente))
            throw new IllegalArgumentException("Utente non registrato");

        this.utenteCorrente = utenteCorrente;
        libriUtente = storageService.caricaLibreria();
    }


    @Override
    public String utenteCorrente() {
        return utenteCorrente;
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
    public synchronized Set<Libro> getLibriUtente() {
        return new HashSet<>(libriUtente);
    }

    @Override
    public synchronized boolean creaNuovoUtente(String username) {
        if(utentiRegistrati.contains(username))
            return false;
        utentiRegistrati.add(username);
        storageService.salvaUtenti(utentiRegistrati);
        return true;
    }

    @Override
    public synchronized boolean eliminaUtente(String username) {
        if(!utentiRegistrati.contains(username))
            return false;
        utentiRegistrati.remove(username);
        storageService.salvaUtenti(utentiRegistrati);
        return true;
    }

    @Override
    public synchronized boolean aggiungiLibro(Libro libro){
        if(libriUtente.contains(libro))
            return false;
        libriUtente.add(libro);
        storageService.salvaLibreria(libriUtente);
        return true;
    }

    @Override
    public synchronized boolean rimuoviLibro(Libro libro){
        if(!libriUtente.contains(libro))
            return false;
        libriUtente.remove(libro);
        storageService.salvaLibreria(libriUtente);
        return true;
    }

    @Override
    public synchronized boolean modificaLibro(Libro libroOriginale, Libro libroModificato){
        if(!libriUtente.contains(libroOriginale))
            return false;
        libriUtente.remove(libroOriginale);
        libriUtente.add(libroModificato);
        storageService.salvaLibreria(libriUtente);
        return true;
    }

    @Override
    public String toString() {
        return "Libreria{" +
                "storageService=" + storageService +
                ", utentiRegistrati=" + utentiRegistrati +
                ", utenteCorrente='" + utenteCorrente + '\'' +
                ", libriUtente=" + libriUtente +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof AbstractLibreria)) return false;

        AbstractLibreria l = (AbstractLibreria) obj;
        return l.storageService.equals(this.storageService) &&
                l.utentiRegistrati.equals(this.utentiRegistrati) &&
                l.utenteCorrente.equals(this.utenteCorrente) &&
                l.libriUtente.equals(this.libriUtente);
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + storageService.hashCode();
        result = prime * result + utentiRegistrati.hashCode();
        result = prime * result + utenteCorrente.hashCode();
        result = prime * result + libriUtente.hashCode();
        return result;
    }

}
