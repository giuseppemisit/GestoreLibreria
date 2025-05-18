package ordine;

import base.libro.Libro;

import java.util.Comparator;
import java.util.List;

public interface Ordinamento extends Comparator<Libro> {

    /**
     * Ordina la lista di libri passata come parametro.
     * @param libri lista dei libri da ordinare
     */
    void ordina(List<Libro> libri);

}
