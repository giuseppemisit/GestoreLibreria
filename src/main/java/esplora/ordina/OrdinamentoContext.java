package esplora.ordina;

import base.libro.Libro;
import esplora.ordina.utility.OrdinamentoFactory;
import esplora.ordina.utility.TipoOrdinamento;

import java.util.List;

public class OrdinamentoContext {

    private Ordinamento ordinamento;

    public void setOrdinamento(Ordinamento ordinamento) {
        this.ordinamento = ordinamento;
    }

    public void setOrdinamento(TipoOrdinamento tipo) {
        switch (tipo) {
            case TITOLO:
                this.ordinamento = OrdinamentoFactory.creaOrdinamentoPerTitolo();
                break;
            case GENERE:
                this.ordinamento = OrdinamentoFactory.creaOrdinamentoPerGenere();
                break;
            case VALUTAZIONE:
                this.ordinamento = OrdinamentoFactory.creaOrdinamentoPerValutazione();
                break;
            case STATO_LETTURA:
                this.ordinamento = OrdinamentoFactory.creaOrdinamentoPerStatoDiLettura();
                break;
            case ISBN:
                this.ordinamento = OrdinamentoFactory.creaOrdinamentoPerIsbn();
                break;
        }

    }

    public void esegui(List<Libro> libri){
        if(ordinamento == null) return;
        ordinamento.ordina(libri);
    }

}