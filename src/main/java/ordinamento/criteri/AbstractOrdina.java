package ordinamento.criteri;

import base.libro.Libro;
import ordinamento.Ordinamento;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractOrdina implements Ordinamento, Comparator<Libro> {

    public AbstractOrdina(){}

    // Se non sono null li confronto con il metodo confrontaLibriNonNull
    protected abstract int confrontaLibriNonNull(Libro libro1, Libro libro2);

    @Override
    public void ordina(List<Libro> libri){
        if(libri == null) return;
        libri.sort(this);
    }

    @Override
    public int compare(Libro libro1, Libro libro2) {
        Integer risultato = risultatoCasiBase(libro1, libro2);
        if(risultato!=null)
            return risultato;
        return confrontaLibriNonNull(libro1, libro2);
    }

    // Metodo per gestire i casi in cui uno o entrambi i libri sono null
    private Integer risultatoCasiBase(Libro libro1, Libro libro2){
        if(libro1 == null && libro2 == null) return 0;
        if (libro1 == null) return -1;
        if (libro2 == null) return 1;
        return null;
    }

}
