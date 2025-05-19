package repository;

import base.libro.Libro;

import java.util.List;

public interface RepositoryLibri {

    /**
     * Salva un libro nel repository.
     * @param libro il libro da salvare, non può essere null
     * @return true se è stato aggiunto, false altrimenti
     */
    boolean aggiungi(Libro libro);

    /**
     * Rimuove un libro esistente dal repository.
     * @param libro il libro da rimuovere, non può essere null
     * @return true se è stato rimosso, false altrimenti
     */
    boolean rimuovi(Libro libro);

    /**
     * Aggiorna le informazioni del libro nel repository.
     * I nuovi parametri verrano richiesti nel metodo stesso.
     * @param libro il libro da aggiornare
     * @return true se è stato aggiornato, false altrimenti
     */
    boolean aggiorna(Libro libro);

    /**
     * Restituisce una lista ordinata dei libri nel repository.
     * @return lista ordinata dei libri
     */
    List<Libro> applicaOrdinamento();

    /**
     * Restituisce una lista filtrata dei libri nel repository.
     * @return lista filtrata dei libri
     */
    List<Libro> applicaFiltri();

    /**
     * Annulla i filtri impostati nel repository.
     * @return true se sono stati rimossi, false altrimenti
     */
    List<Libro> annullaFiltri();

    /**
     * Restituisce i risultati della ricerca dei libri nel repository.
     * @return lista dei risultati della ricerca
     */
    List<Libro> applicaRicerca();

    /**
     * Annulla la ricerca impostata nel repository.
     * @return true se il reset va a buon fine, false altrimenti
     */
    List<Libro> annullaRicerca();

    /**
     * Restituisce una lista di tutti i libri nel repository.
     * @return lista di tutti i libri
     */
    List<Libro> visualizzaLibriReset();

    /**
     * Carica i dati dal file di salvataggio.
     * @param username username del proprietario del file
     * @return Lista dei libri
     */
    List<Libro> leggiDati(String username);

    /**
     * Salva i dati nel file di salvataggio.
     * @param username username del proprietario del file
     * @return true se il salvataggio va a buon fine, false altrimenti
     */
    boolean salvaDati(String username, List<Libro> libri);

}
