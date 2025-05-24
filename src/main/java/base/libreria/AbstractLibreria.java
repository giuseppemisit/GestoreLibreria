package base.libreria;

import base.libro.Libro;
import salvataggio.StorageService;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractLibreria implements Libreria {

    private final StorageService storageService;

    private final String utenteCorrente;
    private final Set<Libro> libriUtente;


    public AbstractLibreria(StorageService storageService, String utenteCorrente) {
        Objects.requireNonNull(storageService, "StorageService non può essere null");
        Objects.requireNonNull(utenteCorrente, "L'utente corrente non può essere null");

        this.utenteCorrente = utenteCorrente;
        this.storageService = storageService;
        libriUtente = storageService.caricaLibreria();
    }

    @Override
    public String utenteCorrente() {
        return utenteCorrente;
    }

    @Override
    public synchronized Set<Libro> getLibriUtente() {
        return new HashSet<>(libriUtente);
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
                l.utenteCorrente.equals(this.utenteCorrente) &&
                l.libriUtente.equals(this.libriUtente);
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + storageService.hashCode();
        result = prime * result + utenteCorrente.hashCode();
        result = prime * result + libriUtente.hashCode();
        return result;
    }

}
