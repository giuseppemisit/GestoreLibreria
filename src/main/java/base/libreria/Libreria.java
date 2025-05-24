package base.libreria;

import base.libro.Libro;

import java.util.Set;

public interface Libreria {

    /**
     * Restituisce l'utente corrente
     * @return utente corrente
     */
    String utenteCorrente();

    /**
     * Restituisce i libri associati all'utente corrente.
     * @return un set contenente i libri dell'utente corrente
     */
    Set<Libro> getLibriUtente();

    /**
     * Aggiunge il libro alla Libreria
     * @return true se è stato aggiunto, false altrimenti
     */
    boolean aggiungiLibro(Libro libro);


    /**
     * Rimuove il libro dalla libreria
     * @return true se è stato eliminato, false altrimenti
     */
    boolean rimuoviLibro(Libro libro);

    /**
     * modifica un libro presente nella collezione
     * @param libroOriginale libro da modificare
     * @param libroModificato libro modificato
     * @return true se è stato modificato,
     */
    boolean modificaLibro(Libro libroOriginale, Libro libroModificato);

}
