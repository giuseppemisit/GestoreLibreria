package esplora.interroga;

import base.libro.Libro;

public interface Parametri {

    /**
     * Verifica se i parametri sono stati impostati.
     * @return true se i parametri sono null, false altrimenti
     */
    boolean isEmpty();

    /**
     * Verifica se il libro soddisfa i parametri.
     * @param libro libro da controllare
     * @return true se soddisfa i parametri, false altrimenti
     */
    boolean soddisfaCriteri(Libro libro);

}