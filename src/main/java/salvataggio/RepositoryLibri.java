package salvataggio;

import base.libro.Libro;

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

}
