package ordine.criteri;

import base.libro.Libro;

public class OrdinaPerTitolo extends AbstractOrdina {

    public OrdinaPerTitolo(){
        super();
    }

    @Override
    protected int confrontaLibriNonNull(Libro libro1, Libro libro2) {
        String titolo1 = libro1.getTitolo();
        String titolo2 = libro2.getTitolo();

        if (titolo1 == null && titolo2 == null) return 0;
        if (titolo1 == null) return -1;
        if (titolo2 == null) return 1;

        return titolo1.compareToIgnoreCase(titolo2);
    }

}