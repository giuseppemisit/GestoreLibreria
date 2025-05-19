package ordinamento.criteri;

import base.InfoExtra;
import base.libro.Libro;

public class OrdinaPerValutazione extends AbstractOrdina {

    public OrdinaPerValutazione(){
        super();
    }

    @Override
    protected int confrontaLibriNonNull(Libro libro1, Libro libro2) {
        InfoExtra.Valutazione val1 = libro1.getValutazione();
        InfoExtra.Valutazione val2 = libro2.getValutazione();

        if (val1 == null && val2 == null) return 0;
        if (val1 == null) return -1;
        if (val2 == null) return 1;

        // Ordina per punteggio(dal più alto al più basso)
        return Integer.compare(val2.getStelle(), val1.getStelle());
    }

}