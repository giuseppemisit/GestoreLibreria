package ordine.criteri;

import base.Autore;
import base.libro.Libro;

import java.util.Collection;

public class OrdinaPerAutore extends AbstractOrdina {

    public OrdinaPerAutore(){
        super();
    }

    @Override
    public int confrontaLibriNonNull(Libro libro1, Libro libro2) {
        Collection<Autore> autori1 = libro1.getAutori();
        Collection<Autore> autori2 = libro2.getAutori();

        if (autori1.isEmpty() && autori2.isEmpty()) return 0;
        if (autori1.isEmpty()) return -1;
        if (autori2.isEmpty()) return 1;

        Autore autore1 = autori1.iterator().next();
        Autore autore2 = autori2.iterator().next();

        return autore1.toString().compareToIgnoreCase(autore2.toString());
    }

}