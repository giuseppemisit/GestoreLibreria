package esplora;

import base.libro.Libro;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommandEsplora implements CommandEsplora {

    protected final List<Libro> libri;

    public AbstractCommandEsplora(List<Libro> libri){
        if (libri == null)
            throw new IllegalArgumentException("La lista di libri non può essere null");

        this.libri = new ArrayList<>(libri);
    }

    public abstract List<Libro> execute();

}
