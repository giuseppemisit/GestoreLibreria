package struttura.informazioni.autore;

public interface Autore {

    /**
     * Restituisce il nome dell'autore
     * @return String
     */
    String getNome();

    /**
     * Restituisce il cognome dell'autore
     * @return String
     */
    String getCognome();

    /**
     * Restituisce la biografia dell'autore
     * @return String
     */
    String getBiografia();
}
