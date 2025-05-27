package esplora.ordina.criteri;

import base.utility.Autore;
import base.libro.Libro;

import java.util.List;

public class OrdinaPerAutore extends AbstractOrdina {

    public OrdinaPerAutore(){
        super();
    }

    @Override
    public int confrontaLibriNonNull(Libro libro1, Libro libro2) {
        List<Autore> autori1 = libro1.getAutori();
        List<Autore> autori2 = libro2.getAutori();

        if (autori1.isEmpty() && autori2.isEmpty()) return 0;
        if (autori1.isEmpty()) return -1;
        if (autori2.isEmpty()) return 1;

        Autore autore1 = autori1.get(0);
        Autore autore2 = autori2.get(0);

        return autore1.toString().compareToIgnoreCase(autore2.toString());
    }

}