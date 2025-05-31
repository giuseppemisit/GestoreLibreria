package controllo.utenti;

import java.util.List;

public interface UserManager {

    /**
     * Utenti che sono registrati nel sistema.
     * @return username degli utenti presenti nel sistema
     */
    List<String> utentiRegistrati();

    /**
     * Dice se l'username è presente.
     * @param username dell'utente
     * @return true se è presente, false altrimenti
     */
    default boolean esisteUtente(String username){
        return utentiRegistrati().contains(username);
    }

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

}
