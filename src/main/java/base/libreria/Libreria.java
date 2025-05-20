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
     * Utenti che sono registrati nel sistema.
     * @return username degli utenti presenti nel sistema
     */
    Set<String> utentiRegistrati();

    /**
     * Dice se l'username è presente.
     * @param username dell'utente
     * @return true se è presente, false altrimenti
     */
    default boolean esisteUtente(String username){
        return utentiRegistrati().contains(username);
    }

    /**
     * Restituisce i libri associati all'utente corrente.
     * @return un set contenente i libri dell'utente corrente
     */
    Set<Libro> getLibriUtente();

    /**
     * Crea un nuovo utente.
     * @param username del nuovo utente
     * @return true se è stato aggiunto, false altrimenti
     */
    boolean creaNuovoUtente(String username);

    /**
     * Elimina l'utente.
     * @param username dell'utente da eliminare
     * @return true se è stato eliminato, false altrimenti
     */
    boolean eliminaUtente(String username);

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
