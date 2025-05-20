package salvataggio;

import base.libro.Libro;

import java.util.Set;

public interface StorageService {

    /**
     * Carica l'elenco degli utenti registrati dal supporto di archiviazione.
     * @return un set contenente gli username degli utenti registrati
     */
    Set<String> caricaUtenti();

    /**
     * Carica la libreria dal supporto di archiviazione.
     * @return La libreria salvata
     */
    Set<Libro> caricaLibreria();

    /**
     * Salva l'elenco degli utenti registrati.
     * @param utenti un set contenente gli username degli utenti da salvare
     * @return true se il salvataggio è avvenuto con successo, false altrimenti
     */
    boolean salvaUtenti(Set<String> utenti);

    /**
     * Salva la libreria specificata nel supporto di archiviazione.
     * @param libreria la libreria da salvare
     * @return true se il salvataggio è avvenuto con successo, false altrimenti
     */
    boolean salvaLibreria(Set<Libro> libreria);


    /**
     * Elimina la libreria dell'utente.
     * @return true se è stata eliminata, false altrimenti
     */
    boolean eliminaLibreria();

}
