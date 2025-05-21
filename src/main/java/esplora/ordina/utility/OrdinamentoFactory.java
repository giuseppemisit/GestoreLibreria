package esplora.ordina.utility;

import esplora.ordina.Ordinamento;
import esplora.ordina.criteri.*;

public class OrdinamentoFactory {

    public static Ordinamento creaOrdinamentoPerAutore() {
        return new OrdinaPerAutore();
    }

    public static Ordinamento creaOrdinamentoPerGenere() {
        return new OrdinaPerGenere();
    }

    public static Ordinamento creaOrdinamentoPerIsbn() {
        return new OrdinaPerIsbn();
    }

    public static Ordinamento creaOrdinamentoPerStatoDiLettura() {
        return new OrdinaPerStatoDiLettura();
    }

    public static Ordinamento creaOrdinamentoPerTitolo() {
        return new OrdinaPerTitolo();
    }

    public static Ordinamento creaOrdinamentoPerValutazione() {
        return new OrdinaPerValutazione();
    }

}
