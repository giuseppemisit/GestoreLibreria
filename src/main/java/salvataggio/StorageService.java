package salvataggio;

import base.libro.Libro;

import java.util.Collection;

public interface StorageService {

    /**
     * Carica l'elenco degli utenti registrati.
     * @return Collection contenente gli username degli utenti registrati
     */
    Collection<String> caricaUtenti();

    /**
     * Carica la libreria dal supporto di archiviazione.
     * @return La libreria salvata
     */
    Collection<Libro> caricaLibreria();

    /**
     * Salva l'elenco degli utenti registrati.
     * @param utenti una Collection contenente gli username degli utenti da salvare.
     * @return true se il salvataggio è avvenuto con successo, false altrimenti
     */
    boolean salvaUtenti(Collection<String> utenti);

    /**
     * Salva la libreria specificata nel supporto di archiviazione.
     * @param libreria Collection dei libri da salvare
     * @return true se il salvataggio è avvenuto con successo, false altrimenti
     */
    boolean salvaLibreria(Collection<Libro> libreria);


    /**
     * Elimina la libreria dell'utente.
     * @return true se è stata eliminata, false altrimenti
     */
    boolean eliminaLibreria();

}