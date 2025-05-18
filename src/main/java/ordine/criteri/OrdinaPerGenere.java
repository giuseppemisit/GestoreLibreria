package ordine.criteri;

import base.InfoExtra;
import base.libro.Libro;

import java.util.Collection;

public class OrdinaPerGenere extends AbstractOrdina{

    public OrdinaPerGenere(){
        super();
    }

    @Override
    protected int confrontaLibriNonNull(Libro libro1, Libro libro2) {
        Collection<InfoExtra.GenereLibro> generi1 = libro1.getGeneri();
        Collection<InfoExtra.GenereLibro> generi2 = libro2.getGeneri();

        if (generi1.isEmpty() && generi2.isEmpty()) return 0;
        if (generi1.isEmpty()) return -1;
        if (generi2.isEmpty()) return 1;

        InfoExtra.GenereLibro genere1 = generi1.iterator().next();
        InfoExtra.GenereLibro genere2 = generi2.iterator().next();

        return genere1.name().compareToIgnoreCase(genere2.name());
    }

}