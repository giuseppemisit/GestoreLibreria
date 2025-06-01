package esplora;

import base.libro.Libro;

import java.util.List;

public interface CommandEsplora {

    /**
     * Esegue il comando della classe implementante.
     * @return una lista contenente dei libri selezionti dall'esecuzione del comando
     */
    List<Libro> execute();

}
