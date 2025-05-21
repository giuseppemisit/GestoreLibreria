package esplora.ordina;

import base.libro.Libro;

import java.util.List;

public interface Ordinamento{

    /**
     * Ordina la lista di libri passata come parametro.
     * @param libri lista dei libri da ordinare
     */
    void ordina(List<Libro> libri);

}
