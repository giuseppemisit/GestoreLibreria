package esplora.interroga;

import base.libro.Libro;
import base.utility.InfoExtra;

public class ParametriFiltro implements Parametri {

    private final InfoExtra.GenereLibro genere;
    private final InfoExtra.Valutazione valutazione;
    private final InfoExtra.StatoLettura statoLettura;

    public ParametriFiltro(InfoExtra.GenereLibro genere,
                           InfoExtra.Valutazione valutazione,
                           InfoExtra.StatoLettura statoLettura){
        this.genere = genere;
        this.valutazione = valutazione;
        this.statoLettura = statoLettura;
    }

    @Override
    public boolean isEmpty(){
        return this.genere == null &&
                this.valutazione == null &&
                this.statoLettura == null;
    }

    @Override
    public boolean soddisfaCriteri(Libro libro){

        boolean soddisfaGenere = this.genere == null ||
                libro.getGeneri().contains(this.genere);

        boolean soddisfaStatoLettura = this.statoLettura == null ||
                this.statoLettura.equals(libro.getStatoLettura());

        boolean soddisfaValutazione = this.valutazione == null ||
                this.valutazione.equals(libro.getValutazione());

        return soddisfaGenere && soddisfaStatoLettura && soddisfaValutazione;
    }

}
