package ordine.criteri;

import base.libro.Libro;

public class OrdinaPerIsbn extends AbstractOrdina {

    public OrdinaPerIsbn(){
        super();
    }

    @Override
    protected int confrontaLibriNonNull(Libro libro1, Libro libro2) {
        String isbn1 = libro1.getIsbn();
        String isbn2 = libro2.getIsbn();

        if (isbn1 == null && isbn2 == null) return 0;
        if (isbn1 == null) return -1;
        if (isbn2 == null) return 1;

        return isbn1.compareTo(isbn2);
    }

}