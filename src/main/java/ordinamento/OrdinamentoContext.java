package ordinamento;

import base.libro.Libro;

import java.util.List;

public class OrdinamentoContext {

    private Ordinamento ordinamento;

    public void setOrdinamento(Ordinamento ordinamento) {
        this.ordinamento = ordinamento;
    }

    public void esegui(List<Libro> libri){
        if(ordinamento == null) return;
        ordinamento.ordina(libri);
    }

}