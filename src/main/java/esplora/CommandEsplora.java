package esplora;

import base.libro.Libro;

import java.util.List;

public interface CommandEsplora {

    /**
     * Esegue il comando della classe implementante.
     */
    List<Libro> execute();

}
