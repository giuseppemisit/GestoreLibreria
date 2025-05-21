package esplora.interroga;

import base.libro.Libro;
import base.utility.InfoExtra;

import java.util.HashSet;
import java.util.Set;

public class ParametriFiltro implements Parametri {

    private final Set<InfoExtra.GenereLibro> genere;
    private final InfoExtra.Valutazione valutazione;
    private final InfoExtra.StatoLettura statoLettura;

    public ParametriFiltro(InfoExtra.Valutazione valutazione, InfoExtra.StatoLettura statoLettura){
        this.genere = new HashSet<>();
        this.valutazione = valutazione;
        this.statoLettura = statoLettura;
    }

    public boolean aggiungiGenere(InfoExtra.GenereLibro genere){
        if(genere == null)
            return false;
        return this.genere.add(genere);
    }

    @Override
    public boolean isEmpty(){
        return this.genere.isEmpty() &&
                this.valutazione == null &&
                this.statoLettura == null;
    }

    @Override
    public boolean soddisfaCriteri(Libro libro){

        boolean soddisfaGenere = this.genere.isEmpty() ||
                libro.getGeneri().stream().anyMatch(this.genere::contains);

        boolean soddisfaStatoLettura = this.statoLettura == null ||
                this.statoLettura.equals(libro.getStatoLettura());

        boolean soddisfaValutazione = this.valutazione == null ||
                this.valutazione.equals(libro.getValutazione());

        return soddisfaGenere && soddisfaStatoLettura && soddisfaValutazione;
    }

}
