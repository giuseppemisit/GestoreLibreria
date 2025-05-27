package esplora.ordina.criteri;

import base.utility.InfoExtra;
import base.libro.Libro;

public class OrdinaPerStatoDiLettura extends AbstractOrdina {

    public OrdinaPerStatoDiLettura(){
        super();
    }

    @Override
    protected int confrontaLibriNonNull(Libro libro1, Libro libro2) {
        InfoExtra.StatoLettura stato1 = libro1.getStatoLettura();
        InfoExtra.StatoLettura stato2 = libro2.getStatoLettura();

        if (stato1 == null && stato2 == null) return 0;
        if (stato1 == null) return -1;
        if (stato2 == null) return 1;

        return getPriorita(stato1) - getPriorita(stato2);
    }

    private int getPriorita(InfoExtra.StatoLettura stato) {
        switch (stato) {
            case IN_LETTURA: return 0; // Priorità più alta (viene prima)
            case DA_LEGGERE: return 1; // Priorità media
            case LETTO: return 2; // Priorità bassa
            default: return 3;
        }
    }

}