package interrogazione;

public interface Parametri {

    /**
     * Reimposta i parametri a null.
     */
    void reset();

    /**
     * Verifica se i parametri sono stati impostati.
     * @return true se i parametri sono null, false altrimenti
     */
    boolean isEmpty();

}