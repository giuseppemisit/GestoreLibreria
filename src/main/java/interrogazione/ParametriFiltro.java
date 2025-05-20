package interrogazione;

import base.utility.InfoExtra;

import java.util.Set;

public class ParametriFiltro implements Parametri{

    private Set<InfoExtra.GenereLibro> genere;
    private InfoExtra.Valutazione valutazione;
    private InfoExtra.StatoLettura statoLettura;


    private static final ParametriFiltro instance = new ParametriFiltro();

    private ParametriFiltro(){}

    public static ParametriFiltro getInstance(){
        return instance;
    }


    //Metodi set
    public void setGenere(Set<InfoExtra.GenereLibro> genere) {
        this.genere = genere;
    }

    public void setValutazione(InfoExtra.Valutazione valutazione) {
        this.valutazione = valutazione;
    }

    public void setStatoLettura(InfoExtra.StatoLettura statoLettura) {
        this.statoLettura = statoLettura;
    }

    //Metodi get
    public Set<InfoExtra.GenereLibro> getGenere() {
        return genere;
    }

    public InfoExtra.Valutazione getValutazione() {
        return valutazione;
    }

    public InfoExtra.StatoLettura getStatoLettura() {
        return statoLettura;
    }

    @Override
    public void reset(){
        this.genere = null;
        this.valutazione = null;
        this.statoLettura = null;
    }

    @Override
    public boolean isEmpty(){
        return this.genere == null && this.valutazione == null && this.statoLettura == null;
    }

}
